package methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import knowledgeBase.KnowledgeBase;
import proposition.ComplexSentence;
import proposition.Sentence;
import proposition.SimpleSentence;

/**
 * Implementation of the Forward Chaining Algorithm (FC) - as defined in AIMA 4th ed, pages 230-231. This implementation
 * assumes that the given input file is correct an contains strictly definite cluases/
 * 
 * @author Zoie Tad-y 
 */

/**
 * determines if a single proposition
 * symbol q, the query, is entailed by a knowledge base of definite clauses. It
 * begins from
 * known facts (positive literals) in the knowledge base. If all the premises of
 * an implication are
 * known, then its conclusion is added to the set of known facts (pp 230-231
 * AIMA 4th ed)
 */
public class PLFCEntails {
    // is meant to catch these senteces once they are processed.
    private List<Sentence> agendaCatcher;

    /**
     * check if a query is true or not in the context of the knowledgebase
     * 
     * general flow of logic:
     * for each agenda (symbol known to be true)
     * if it is the query, then the query is true
     * otherwise, if it is still yet to be processed
     * assign it to be true, and for each clause where the agenda is
     * a premise, decrement the count of premises to be proven for that sentence
     * if the sentence has no premise left to be proven
     * add it to the container of known facts
     * 
     * @param kb - knowledgebase of query
     * @param q  - query
     * @return - truth value of query in the context of the kb
     */
    public boolean isEntailed(KnowledgeBase kb, Sentence q) {
        // used for tracking sentences traversed in the logic
        this.agendaCatcher = new ArrayList<Sentence>();

        // a table, where count[c] is initially the number of symbols in clause c’s
        // premise
        Map<Sentence, Integer> count = initializeCount(kb);
        // a table, where inferred[s] is initially false for all symbols
        Map<Sentence, Boolean> inferred = initializeInferred(kb);
        // a queue of symbols, initially symbols known to be true in kb
        Queue<Sentence> agenda = initializeAgenda(kb);
        // mapping of where (which clauses) a symbol is a premise
        Map<Sentence, Set<Sentence>> pToClauseMap = initializePToClauseMap(count, inferred);

        // until all agenda is processed
        while (!agenda.isEmpty()) {
            // get an agenda
            Sentence p = agenda.remove();
            // note it as processed
            this.agendaCatcher.add(p);

            // if it is the query then return true
            if (p.equals(q)) {
                return true;
            }
            // otherwise check if it has been processed before
            if (inferred.get(p).equals(Boolean.FALSE)) {
                // if not, mark it as processed
                inferred.put(p, true);
                // for each clase that the sentence is a premise
                for (Sentence c : pToClauseMap.get(p)) {
                    // decrement the count of premises to be proven true
                    decrement(count, c);
                    if (count.get(c) == 0) {
                        // if none are left to be proven for this sentence
                        // add the conclusion as a known fact that is yet to be processed
                        ComplexSentence cs = (ComplexSentence) c;
                        agenda.add(getConclusion(cs));
                    }
                }
            }
        }

        // otherwise, return false
        return false;
    }

    // getter for the agenda catcher
    public List<Sentence> getAgendaCatcher() {
        return this.agendaCatcher;
    }

    /**
     * Initialise table for counter of known symbols in a premise of each clause in
     * the knowledgebase kb
     * 
     * direct adaptation of the count table in Figure 7.15, page 231 of the AIMA 4th
     * ed.
     * 
     * @param kb - knowledgebase of query
     * @return - mapping of sentences to the number of symbols in the premise yet to
     *         be proven
     */
    private Map<Sentence, Integer> initializeCount(KnowledgeBase kb) {
        // initialize counting table 
        Map<Sentence, Integer> count = new HashMap<Sentence, Integer>();

        // convert sentences in the kb as set collection of sentences
        Set<Sentence> clauses = new HashSet<>(kb.getSentences());

        // intialise container for all complex sentences in kb
        Set<Sentence> filteredClauses = new HashSet<>();

        // foreach sentence in kb
        for (Sentence s : clauses) {
            // if they are singluar statements or propositional symbols
            if (s instanceof SimpleSentence) {
                continue;
            }

            // if they are complex sentences (i.e. statment of implication or conjunction)
            if (s instanceof ComplexSentence) {
                ComplexSentence cs = (ComplexSentence) s;
                // add them to the set of complex sentences
                filteredClauses.add(cs);
            }
        }

        // for each complex sentence
        for (Sentence c : filteredClauses) {
            if (c instanceof ComplexSentence) {
                ComplexSentence cs = (ComplexSentence) c;
                // if they are sentences of implication
                if (cs.getConnective().equals("=>")) {
                    // then get the number of symbol in its premise
                    // add it to the count table
                    count.put(cs, cs.getSimplerSentence(0).getSymbols().size());
                }
            }
        }

        // return count table
        return count;
    }

    /**
     * inferred is a table of all symbols in the kb initially assumed to be false.
     * direct adaptation of inferred table in Figure 7.15, page 231 of the AIMA 4th ed.
     * 
     * @param kb - knowledge base of query
     * @return - mapping of each symbol and an initial assignment of false
     */
    private Map<Sentence, Boolean> initializeInferred(KnowledgeBase kb) {
        // initialise map container of symbol and respective boolean value
        Map<Sentence, Boolean> inferred = new HashMap<Sentence, Boolean>();
        // foreach symbol in the knowledgebase
        for (String s : kb.getSymbols()) {
            // convert each symbol and initialise its truth value as false
            inferred.put(new SimpleSentence(s), false);
        }
        // return inferred table
        return inferred;
    }

    /**
     * agenda is queue or container that keeps track of symbols known to be true but yet to be processed.
     * direct adaptation of agenda as described in Figure 7.15, page 231 of the AIMA 4th ed.
     *  
     * @param kb - knowledge base of query
     * @return - list of sentences symbols known to be true but yet to be processed
     */
    private Queue<Sentence> initializeAgenda(KnowledgeBase kb) {
        // initialise containing list
        Queue<Sentence> initiallyKnown = new LinkedList<Sentence>();
        // foreach sentence in kb
        for (Sentence s : kb.getSentences()) {
            // if it is a single statement
            if (s.getSymbols().size() == 1) {
                // add it to the list of known to be true symbols
                initiallyKnown.add(s);
            }
        }
        // return containing list
        return initiallyKnown;
    }

    /**
     * mapping of where each sentence or symbol is a premise 
     * symplifies the process of identifying which sentence to process next
     * 
     * note: in theory, there is a distinction between propositional symbols and propositional sentences
     * however for the purposes of this implementation, the author assumed them to be the same.
     * 
     * @param count - count table
     * @param inferred - inffered table
     * @return - mapping of each symbol and where they are used as premise
     */
    private Map<Sentence, Set<Sentence>> initializePToClauseMap(Map<Sentence, Integer> count,
            Map<Sentence, Boolean> inferred) {
        // initialise containg map         
        Map<Sentence, Set<Sentence>> pToClausesWithPInPremise = new HashMap<Sentence, Set<Sentence>>();

        // for each symbol in the inferred table
        for (Sentence p : inferred.keySet()) {
            // initialise set to contain sentences
            Set<Sentence> clausesWithPInPremise = new HashSet<Sentence>();
            // foreach sentence in the count table
            for (Sentence c : count.keySet()) {
                // if it is an instance of a complex sentence (i.e. contains a connective)
                if ((c instanceof ComplexSentence)) {
                    ComplexSentence cs = (ComplexSentence) c;
                    // if the sentence is a statement of implication, and infers from statement referencing p
                    if (cs.getConnective().equals("=>") && cs.getSimplerSentence(0).contains(p)) {
                        // then add it to the set of clauses where p is used as premise
                        clausesWithPInPremise.add(cs);
                    }
                }
            }
            // add statement to clauses mapping to overall map
            pToClausesWithPInPremise.put(p, clausesWithPInPremise);
        }

        // return map
        return pToClausesWithPInPremise;
    }

    /**
     * decrement count of premise to be proven
     * 
     * @param count count table
     * @param c sentence to reduce number of premises to be proven
     */
    private void decrement(Map<Sentence, Integer> count, Sentence c) {
        int currentCount = count.get(c);
        count.put(c, currentCount - 1);
    }

    /**
     * get the conclusion of a implication statement
     * @param cs complex sentence 
     * @return conclusion of sentence
     */
    private Sentence getConclusion(ComplexSentence cs) {
        return cs.getSimplerSentence(1);
    }

}
