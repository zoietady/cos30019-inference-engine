import java.util.Collections;
import java.util.List;

import knowledgeBase.KnowledgeBase;
import methods.PLBCEntails;
import methods.PLFCEntails;
import methods.TTEntails;
import parser.PropositionalLogicParser;
import proposition.Sentence;

public class App {
    public static void main(String[] args) throws Exception {

        PropositionalLogicParser parser = new PropositionalLogicParser(args[1]);

        KnowledgeBase parsedKnowledgeBase = new KnowledgeBase(parser.getSentences());
        Sentence query = parser.getQuery();

        switch (args[0]) {
            case "TT":
                TTEntails tt = new TTEntails();
                ttResult(tt.isEntailed(parsedKnowledgeBase, query), tt.getNumberOfModels());
                break;
            case "FC":
                PLFCEntails fc = new PLFCEntails();
                fcResult(fc.isEntailed(parsedKnowledgeBase, query), fc.getAgendaCatcher());
                break;
            case "BC":
                PLBCEntails bc = new PLBCEntails();
                bcResult(bc.isEntailed(parsedKnowledgeBase, query), bc.getAgendaCatcher());
                break;
            default:
                System.out.println("invalid input");
        }
    }

    public static void ttResult(boolean b, int num) {
        if (b) {
            System.out.println("YES : " + num);
        } else {
            System.out.println("NO");
        }
    }

    public static void fcResult(boolean b, List<Sentence> agenda) {
        if (b) {
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

    public static void bcResult(boolean b, List<Sentence> agenda) {
        if (b) {
            System.out.print("YES : ");
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
