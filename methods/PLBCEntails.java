package methods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
// import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import knowledgeBase.KnowledgeBase;
import proposition.ComplexSentence;
import proposition.Sentence;
import proposition.SimpleSentence;

public class PLBCEntails {
    private List<Sentence> agendaCatcher;

    // given a query
    // if it is already a known, then return true
    // otherwise get all clauses that imply q
    // if one of the clauses has a premise that is know to be true, then return true
    // else query symbols in the premise of the clause
    public boolean isEntailed(KnowledgeBase kb, Sentence q) {

        this.agendaCatcher = new ArrayList<Sentence>();

        // keeps track of the number of yet to be inferred symbols in each of the
        // premise
        // if count is zero then the premise is true
        Map<Sentence, Integer> count = initializeCount(kb);

        // System.out.println("count");
        // for (Sentence name : count.keySet()) {
        // String key = name.toString();
        // String value = count.get(name).toString();
        // System.out.println(key + " " + value);
        // }
        // inferred <- a table, where inferred[s] is initially false for all
        // symbols

        // keeps track of the truth values of each symbol in the kb
        Map<Sentence, Boolean> inferred = initializeInferred(kb);

        // System.out.println("inferred");
        // for (Sentence name : inferred.keySet()) {
        // String key = name.toString();
        // String value = inferred.get(name).toString();
        // System.out.println(key + " " + value);
        // }

        // a list containing all the known to be true symbols in the kb
        List<Sentence> knownFacts = initializeAgenda(kb);

        Stack<Sentence> agenda = new Stack<Sentence>();
        agenda.add(q);

        // System.out.println("agenda");
        // for (Sentence name : agenda) {
        // System.out.println(name);
        // }

        // map where the key is a symbol, the values are the clauses where the symbol is
        // the conclusion
        Map<Sentence, Set<Sentence>> pToClauseMap = initializePToClauseMap(count, inferred);

        // System.out.println("index");
        // for (Sentence name : pToClausesWithPInPremise.keySet()) {
        // String key = name.toString();
        // String value = pToClausesWithPInPremise.get(name).toString();
        // System.out.println(key + " " + value);
        // }

        while (!agenda.isEmpty()) {
            Sentence p = agenda.pop();

            agendaCatcher.add(p);

            if (knownFacts.contains(p)) {
                return true;
            }

            Set<Sentence> clausesImplyingP = pToClauseMap.get(p);

            if (clausesImplyingP.isEmpty()) {
                continue;
            }

            for (Sentence sentence : clausesImplyingP) {
                ComplexSentence cs = (ComplexSentence) sentence;
                for (Sentence symbol : getSymoblsInPremise(cs)) {
                    if (knownFacts.contains(symbol)) {
                        decrement(count, sentence);
                    }
                }

                if (count.get(sentence) == 0) {
                    knownFacts.add(sentence);
                }

                agenda.add(getPremise(cs));
            }
        }

        return false;
    }

    public boolean TestRBC(KnowledgeBase kb, Sentence q) {

        this.agendaCatcher = new ArrayList<Sentence>();
        Map<Sentence, Integer> count = initializeCount(kb);
        Map<Sentence, Boolean> inferred = initializeInferred(kb);
        List<Sentence> knownFacts = initializeAgenda(kb);
        Stack<Sentence> agenda = new Stack<Sentence>();
        agenda.add(q);
        Map<Sentence, Set<Sentence>> pToClauseMap = initializePToClauseMap(count, inferred);

        return BC(q, knownFacts, pToClauseMap);
    }

    private boolean BC(Sentence query,List<Sentence> knownFacts, Map<Sentence, Set<Sentence>> pToClauseMap) 
    {
        agendaCatcher.add(query);

        if (knownFacts.contains(query)) {
            return true;
        }

        if(query.hasConnective()){
            ComplexSentence cs = (ComplexSentence) query;
            return BC(cs.getSimplerSentence(0), knownFacts,pToClauseMap) && BC(cs.getSimplerSentence(1), knownFacts, pToClauseMap);
        }

        for (Sentence sentence : pToClauseMap.get(query)) {
            ComplexSentence clause = (ComplexSentence) sentence;
            return BC(getPremise(clause), knownFacts, pToClauseMap);
        }

        return false;
    }

    public List<Sentence> getAgendaCatcher() {
        return this.agendaCatcher;
    }

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
        // for (Sentence s : kb.getSentences()) {
        // if (s instanceof SimpleSentence) {
        // inferred.put(s, false);
        // } else {
        // inferred.put(s, false);
        // }
        // }

        for (String s : kb.getSymbols()) {
            inferred.put(new SimpleSentence(s), false);
        }
        return inferred;
    }

    private List<Sentence> initializeAgenda(KnowledgeBase kb) {
        // agenda <- a queue of symbols, initially symbols known to be true in
        // KB
        List<Sentence> initiallyKnown = new LinkedList<Sentence>();
        // for (Sentence c : count.keySet()) {
        // // No premise just a conclusion, then we know its true
        // if (c.getSymbols().size() == 1) {
        // initiallyKnown.add(c);
        // }
        // }

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

    private void decrement(Map<Sentence, Integer> count, Sentence c) {
        int currentCount = count.get(c);
        count.put(c, currentCount - 1);
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
