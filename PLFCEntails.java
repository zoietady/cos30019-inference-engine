import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class PLFCEntails {
    private List<Sentence> agendaCatcher;  

    public boolean isEntailed(KnowledgeBase kb, Sentence q) {

        this.agendaCatcher = new ArrayList<Sentence>();
        Map<Sentence, Integer> count = initializeCount(kb);

        // System.out.println("count");
        // for (Sentence name : count.keySet()) {
        //     String key = name.toString();
        //     String value = count.get(name).toString();
        //     System.out.println(key + " " + value);
        // }
        // inferred <- a table, where inferred[s] is initially false for all
        // symbols
        Map<Sentence, Boolean> inferred = initializeInferred(kb);

        // System.out.println("inferred");
        // for (Sentence name : inferred.keySet()) {
        //     String key = name.toString();
        //     String value = inferred.get(name).toString();
        //     System.out.println(key + " " + value);
        // }
        // agenda <- a queue of symbols, initially symbols known to be true in
        // KB
        Queue<Sentence> agenda = initializeAgenda(kb);

        // System.out.println("agenda");
        // for (Sentence name : agenda) {
        //     System.out.println(name);
        // }
        // Note: an index for p to the clauses where p appears in the premise
        Map<Sentence, Set<Sentence>> pToClauseMap = initializePToClauseMap(count, inferred);
        // System.out.println("index");
        // for (Sentence name : pToClausesWithPInPremise.keySet()) {
        //     String key = name.toString();
        //     String value = pToClausesWithPInPremise.get(name).toString();
        //     System.out.println(key + " " + value);
        // }

        while (!agenda.isEmpty()) {
            Sentence p = agenda.remove();
            this.agendaCatcher.add(p);
            if (p.equals(q)) {
                return true;
            }
            if (inferred.get(p).equals(Boolean.FALSE)) {
                inferred.put(p, true);
                for (Sentence c : pToClauseMap.get(p)) {
                    decrement(count, c);
                    if (count.get(c) == 0) {
                        ComplexSentence cs = (ComplexSentence) c;
                        agenda.add(getConclusion (cs));
                    }
                }
            }
        }

        return false;
    }

    public List<Sentence> getAgendaCatcher(){
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

    private Queue<Sentence> initializeAgenda(KnowledgeBase kb) {
        // agenda <- a queue of symbols, initially symbols known to be true in
        // KB
        Queue<Sentence> initiallyKnown = new LinkedList<Sentence>();
        // for (Sentence c : count.keySet()) {
        //     // No premise just a conclusion, then we know its true
        //     if (c.getSymbols().size() == 1) {
        //         initiallyKnown.add(c);
        //     }
        // }

        for (Sentence s : kb.getSentences()) {
            if (s.getSymbols().size() == 1) {
            initiallyKnown.add(s);
            }
        }
        return initiallyKnown;
    }

    private Map<Sentence, Set<Sentence>> initializePToClauseMap(Map<Sentence, Integer> count,Map<Sentence, Boolean> inferred) {
        Map<Sentence, Set<Sentence>> pToClausesWithPInPremise = new HashMap<Sentence, Set<Sentence>>();

        for (Sentence p : inferred.keySet()) {
            Set<Sentence> clausesWithPInPremise = new HashSet<Sentence>();
            for (Sentence c : count.keySet()) {
                if ((c instanceof ComplexSentence)) {
                    ComplexSentence cs = (ComplexSentence) c;
                    if (cs.getConnective().equals("=>") && cs.getSimplerSentence(0).contains(p)){
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

    private Sentence getConclusion(ComplexSentence cs){
        return cs.getSimplerSentence(1);
    }

}
