import java.util.Collections;
import java.util.List;

import knowledgeBase.KnowledgeBase;
import methods.PLBCEntails;
import methods.PLFCEntails;
import methods.TTEntails;
import parser.PropositionalLogicParser;
import proposition.Sentence;

/**
 * Main application of the inference engine
 * 
 * @author Zoie Tad-y 
 */
public class App {
    public static void main(String[] args) throws Exception {

        // create parser
        PropositionalLogicParser parser = new PropositionalLogicParser(args[1]);

        // get the parsed sentences
        KnowledgeBase parsedKnowledgeBase = new KnowledgeBase(parser.getSentences());
        // get the parsed query
        Sentence query = parser.getQuery();

        // check for the method that the user input
        switch (args[0]) {
            case "TT":
                // truth table entails
                TTEntails tt = new TTEntails();
                ttResult(tt.isEntailed(parsedKnowledgeBase, query), tt.getNumberOfModels());
                break;
            case "FC":
                // forward chaining
                PLFCEntails fc = new PLFCEntails();
                fcResult(fc.isEntailed(parsedKnowledgeBase, query), fc.getAgendaCatcher());
                break;
            case "BC":
                // backward chaining
                PLBCEntails bc = new PLBCEntails();
                bcResult(bc.isEntailed(parsedKnowledgeBase, query), bc.getAgendaCatcher());
                break;
            default:
                System.out.println("invalid input");
        }
    }

    /**
     * dipslay the result of a tt enatils
     * @param b - result
     * @param num - num of models
     */
    public static void ttResult(boolean b, int num) {
        if (b) {
            // When the method is TT and the answer is YES, it should be followed by a colon (:) and the number of models of KB
            System.out.println("YES : " + num);
        } else {
            System.out.println("NO");
        }
    }

    /**
     * dipslay the result of fc
     * @param b bool result
     * @param agenda list of symbols/sentences processed
     */
    public static void fcResult(boolean b, List<Sentence> agenda) {
        if (b) {
            // When the method is FC or BC and the answer is YES, it should be followed by a colon (:) and the list of propositional 
            // symbols entailed from KB that has been found during the execution of the specified algorithm
            System.out.print("YES : ");
            for (Sentence s : agenda) {
                System.out.print(s);
                if (agenda.indexOf(s) < agenda.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        } else {
            System.out.println("NO");
        }
    }

    /**
     * dipslay the result of bc
     * @param b bool result
     * @param agenda list of symbols/sentences processed
     */
    public static void bcResult(boolean b, List<Sentence> agenda) {
        if (b) {
            // When the method is FC or BC and the answer is YES, it should be followed by a colon (:) and the list of propositional 
            // symbols entailed from KB that has been found during the execution of the specified algorithm
            System.out.print("YES : ");
            // required to be reversed since bc first "processes" the from the query then back
            Collections.reverse(agenda);
            for (Sentence s : agenda) {
                System.out.print(s);
                if (agenda.indexOf(s) < agenda.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        } else {
            System.out.println("NO");
        }
    }

}
