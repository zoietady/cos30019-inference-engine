package methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import knowledgeBase.KnowledgeBase;
import proposition.ComplexSentence;
import proposition.Sentence;
import proposition.SimpleSentence;

/**
 * Implementation of the Backward Chaining Algorithm (BC) - as defined in AIMA 4th ed, pages 230-231
 * 
 * @author Zoie Tad-y 
 */


/**
 * The backward-chaining algorithm, as its name suggests, works backward from the query. 
 * If the query q is known to be true, then no work is needed. Otherwise, the algorithm finds 
 * those implications in the knowledge base whose conclusion is q. If all the premises of one of
 * those implications can be proved true (by backward chaining), then q is true. (pp 230-231 AIMA 4th ed)
 */
public class PLBCEntails {
    // is meant to catch these senteces once they are processed.
    private List<Sentence> agendaCatcher;

    /**
     * check if a query is true or not in the context of the knowledgebase
     * 
     * general flow of logic:
     * given a query
     * if it is already a known, then return true
     * otherwise get all clauses that imply q
     * if one of those clauses has a premise that is know to be true, then return true
     * else query symbols in the premise of the clause.
     * 
     * @param kb - knowledgebase
     * @param q - query
     * @return truth value of query in the context of the kb
     */
    public boolean isEntailed(KnowledgeBase kb, Sentence q) {
        // used for tracking sentences traversed in the logic
        this.agendaCatcher = new ArrayList<Sentence>();

        // More extensively used in FC but here is just used to
        // for mapping sentences to clauses where they are implied
        Map<Sentence, Integer> count = initializeCount(kb);
        Map<Sentence, Boolean> inferred = initializeInferred(kb);

        // Known facts in the knowledgebase 
        // where symbols are stated as is
        List<Sentence> knownFacts = initializeAgenda(kb);

        // mapping of sentence to clauses where they are implied
        Map<Sentence, Set<Sentence>> pToClauseMap = initializePToClauseMap(count, inferred);

        // given a mapping of all the sentences that are implied to clauses where they are implied
        // and a list of known facts
        // recursively determine if the query is true or not
        return RBC(q, knownFacts, pToClauseMap);
    }

    /**
     * recursive implementation of the backward chaining algo
     * 
     * @param query - the statement that we wish to check if its true
     * @param knownFacts - the set of known facts
     * @param pToClauseMap - mapping of sentences to clauses where they are implied or partially implied
     * @return boolean value - whether the query is true or not in the context of the knowledge base
     */
    private boolean RBC(Sentence query, List<Sentence> knownFacts, Map<Sentence, Set<Sentence>> pToClauseMap) {
        agendaCatcher.add(query);

        // if a query is known to be true, then no work is needed (return true)
        if (knownFacts.contains(query)) {
            return true;
        }

        // if a query is complex, then check if its simpler sentences can be proven true via bc
        if (query.hasConnective()) {
            ComplexSentence cs = (ComplexSentence) query;
            
            // If all the premises of one of those implications can be proved true (by backward chaining), then q is true
            return RBC(cs.getSimplerSentence(0), knownFacts, pToClauseMap) && RBC(cs.getSimplerSentence(1), knownFacts, pToClauseMap);
        }

        // otherwise,
        // ... the algorithm finds those implications in the knowledge base whose conclusion is q. 
        for (Sentence sentence : pToClauseMap.get(query)) {
            ComplexSentence clause = (ComplexSentence) sentence;
            // If all the premises of one of those implications can be proved true (by backward chaining), then q is true
            return RBC(getPremise(clause), knownFacts, pToClauseMap);
        }

        // if none of those implications is premised with someting that can be proven true, then return false.
        // then q is false
        return false;
    }

    // getter for the agenda catcher
    // AIMA refers to as agenda as the tracker for symbols to be knwon true
    // but not yet processed, hence in this case the agenda catcher
    // is meant to catch these symbols once they are processed.
    public List<Sentence> getAgendaCatcher() {
        return this.agendaCatcher;
    }

    /**
     * Initialise table for counter of known symbols in a premise of each clause in the knowledgebase kb
     * 
     * note: orginally use for tracking proven to be true premises in the iterative implementation
     * of this algorithm/method. Later converted as refence for creating mapping of sentences and where they are implied.
     * 
     * direct adaptation of the count table in Figure 7.15, page 231 of the AIMA 4th ed.
     * 
     * @param kb - knowledgebase of query
     * @return - mapping of sentences to the number of symbols in the premise yet to be proven
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
                // ignore
                continue;
            }

            // if they are complex sentences (i.e. statment of implication or conjunction)
            if (s instanceof ComplexSentence) {
                // add them to the set of complex sentences
                ComplexSentence cs = (ComplexSentence) s;
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
     * for BC is used for checking what symbols are already known.
     * @param kb - knowledge base of query
     * @return - list of sentences symbols known to be true but yet to be processed
     */
    private List<Sentence> initializeAgenda(KnowledgeBase kb) {
        // initialise containing list
        List<Sentence> initiallyKnown = new LinkedList<Sentence>();

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
     * mapping of where each sentence or symbol is being inffered 
     * symplifies the process of identifying which sentence to process next
     * 
     * note: in theory, there is a distinction between propositional symbols and propositional sentences
     * however for the purposes of this implementation, the author assumed them to be the same.
     * 
     * @param count - count table
     * @param inferred - inffered table
     * @return - mapping of each symbol and where they are being inffered
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
                    // if the sentence is a statement of implication, and infers a statement reference p
                    ComplexSentence cs = (ComplexSentence) c;
                    if (cs.getConnective().equals("=>") && cs.getSimplerSentence(1).contains(p)) {
                        // then add it to the set of clauses inferring p
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
     * given a complex sentence (assuming that it is a statement of implication), get its premise
     * 
     * @param cs - complex sentence
     * @return - get premise
     */
    private Sentence getPremise(ComplexSentence cs) {
        return cs.getSimplerSentence(0);
    }

    /**
     * Used for debugging purposes
     * Construct a set of all the symbols in a complex sentence
     * 
     * @param cs - complex sentence to get premises from
     * @return
     */
    private Set<Sentence> getSymoblsInComplexSentence(ComplexSentence cs) {
        Set<Sentence> symbolsInPremise = new HashSet<Sentence>();
        // for each string symbol in a clause
        for (String s : cs.getSymbols()) {
            // convert it into a sentence object and and push to the set collection
            symbolsInPremise.add(new SimpleSentence(s));
        }

        // return set collection of symbols
        return symbolsInPremise;
    }
}
