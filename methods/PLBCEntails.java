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
 * The backward-chaining algorithm, as its name suggests, works backward from the query. 
 * If the query q is known to be true, then no work is needed. Otherwise, the algorithm finds 
 * those implications in the knowledge base whose conclusion is q. If all the premises of one of
 * those implications can be proved true (by backward chaining), then q is true. (pp 231-232 AIMA 4th ed)
 */
public class PLBCEntails {
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
     * @return truth value of te query in the context of the kb
     */
    public boolean isEntailed(KnowledgeBase kb, Sentence q) {
        // used for tracking symbols traversed in the logic
        this.agendaCatcher = new ArrayList<Sentence>();

        // More extensive used in FC but here is just used to
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

    // 
    private Map<Sentence, Integer> initializeCount(KnowledgeBase kb) {
        Map<Sentence, Integer> count = new HashMap<Sentence, Integer>();

        Set<Sentence> clauses = new HashSet<>(kb.getSentences());

        Set<Sentence> filteredClauses = new HashSet<>(kb.getSentences());
        for (Sentence s : clauses) {
            if (s instanceof SimpleSentence) {
                continue;
            }

            if (s instanceof ComplexSentence) {
                ComplexSentence cs = (ComplexSentence) s;
                filteredClauses.add(cs);
            }
        }
        for (Sentence c : filteredClauses) {
            if (c instanceof ComplexSentence) {
                ComplexSentence cs = (ComplexSentence) c;
                if (cs.getConnective().equals("=>")) {
                    count.put(cs, cs.getSimplerSentence(0).getSymbols().size());
                }
            }
        }

        return count;
    }

    private Map<Sentence, Boolean> initializeInferred(KnowledgeBase kb) {
        Map<Sentence, Boolean> inferred = new HashMap<Sentence, Boolean>();
        for (String s : kb.getSymbols()) {
            inferred.put(new SimpleSentence(s), false);
        }
        return inferred;
    }

    private List<Sentence> initializeAgenda(KnowledgeBase kb) {
        List<Sentence> initiallyKnown = new LinkedList<Sentence>();

        for (Sentence s : kb.getSentences()) {
            if (s.getSymbols().size() == 1) {
                initiallyKnown.add(s);
            }
        }

        return initiallyKnown;
    }

    private Map<Sentence, Set<Sentence>> initializePToClauseMap(Map<Sentence, Integer> count,
            Map<Sentence, Boolean> inferred) {
        Map<Sentence, Set<Sentence>> pToClausesWithPInPremise = new HashMap<Sentence, Set<Sentence>>();

        for (Sentence p : inferred.keySet()) {
            Set<Sentence> clausesWithPInPremise = new HashSet<Sentence>();
            for (Sentence c : count.keySet()) {
                if ((c instanceof ComplexSentence)) {
                    ComplexSentence cs = (ComplexSentence) c;
                    if (cs.getConnective().equals("=>") && cs.getSimplerSentence(1).contains(p)) {
                        clausesWithPInPremise.add(cs);
                    }
                }
            }
            pToClausesWithPInPremise.put(p, clausesWithPInPremise);
        }

        return pToClausesWithPInPremise;
    }

    private Sentence getPremise(ComplexSentence cs) {
        return cs.getSimplerSentence(0);
    }

    private Set<Sentence> getSymoblsInPremise(ComplexSentence cs) {
        Set<Sentence> symbolsInPremise = new HashSet<Sentence>();
        for (String s : cs.getSymbols()) {
            symbolsInPremise.add(new SimpleSentence(s));
        }

        return symbolsInPremise;
    }
}
