import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");

        ArrayList<Sentence> finalListOfSentences = new ArrayList<Sentence>();

        SimpleSentence b = new SimpleSentence("b");
        SimpleSentence b2 = new SimpleSentence("b");
        SimpleSentence e = new SimpleSentence("e");
        SimpleSentence p2 = new SimpleSentence("p2");
        SimpleSentence p3 = new SimpleSentence("p3");
        SimpleSentence p1 = new SimpleSentence("p1");
        SimpleSentence f = new SimpleSentence("f");
        SimpleSentence d = new SimpleSentence("d");
        SimpleSentence a = new SimpleSentence("a");
        SimpleSentence g = new SimpleSentence("g");
        SimpleSentence c = new SimpleSentence("c");
        SimpleSentence h = new SimpleSentence("h");


        BufferedReader inputFile = new BufferedReader(new FileReader(args[0]));
        ReadFile readFile = new ReadFile();

        ArrayList<Sentence> passedSentences = new ArrayList<Sentence>();

        readFile.readInput(inputFile);

        passedSentences = readFile.GetSentences();

        System.out.println(passedSentences.toString());


        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        sentences.add(p2);
        sentences.add(p3);
        ComplexSentence testComplexSentence = new ComplexSentence(sentences, "=>");
        finalListOfSentences.add(testComplexSentence);

        // sentences = new ArrayList<Sentence>();
        // sentences.add(h);
        // sentences.add(p3);
        // testComplexSentence = new ComplexSentence(sentences, "=>");
        // finalListOfSentences.add(testComplexSentence);

        sentences = new ArrayList<Sentence>();
        sentences.add(p3);
        sentences.add(p1);

        testComplexSentence = new ComplexSentence(sentences, "=>");
        finalListOfSentences.add(testComplexSentence);

        sentences = new ArrayList<Sentence>();
        sentences.add(c);
        sentences.add(e);

        testComplexSentence = new ComplexSentence(sentences, "=>");
        finalListOfSentences.add(testComplexSentence);

        sentences = new ArrayList<Sentence>();
        sentences.add(b);
        sentences.add(e);

        testComplexSentence = new ComplexSentence(sentences, "&");

        sentences = new ArrayList<Sentence>();
        sentences.add(testComplexSentence);
        sentences.add(f);

        ComplexSentence testComplexSentence2 = new ComplexSentence(sentences, "=>");

        finalListOfSentences.add(testComplexSentence2);

        sentences = new ArrayList<Sentence>();
        sentences.add(f);
        sentences.add(g);

        testComplexSentence = new ComplexSentence(sentences, "&");

        sentences = new ArrayList<Sentence>();
        sentences.add(testComplexSentence);
        sentences.add(h);

        testComplexSentence2 = new ComplexSentence(sentences, "=>");

        finalListOfSentences.add(testComplexSentence2);

        sentences = new ArrayList<Sentence>();
        sentences.add(p1);
        sentences.add(d);

        testComplexSentence = new ComplexSentence(sentences, "=>");
        finalListOfSentences.add(testComplexSentence);

        sentences = new ArrayList<Sentence>();
        sentences.add(p1);
        sentences.add(p3);

        testComplexSentence = new ComplexSentence(sentences, "&");

        sentences = new ArrayList<Sentence>();
        sentences.add(testComplexSentence);
        sentences.add(c);

        testComplexSentence2 = new ComplexSentence(sentences, "=>");

        finalListOfSentences.add(testComplexSentence2);

        finalListOfSentences.add(a);
        finalListOfSentences.add(b);
        finalListOfSentences.add(p2);

        KnowledgeBase kb2 = new KnowledgeBase(finalListOfSentences);

        KnowledgeBase kb = new KnowledgeBase(passedSentences);

        // kb.listSymbols();
        // kb.listSentences();
        // kb2.listSymbols();
        // kb2.listSentences();
        // System.out.println(finalListOfSentences == passedSentences);
        // System.out.println(passedSentences.size());
        // System.out.println(finalListOfSentences.size());

        TTEntails tt = new TTEntails();

        // System.out.println(tt.isEntailed(kb, d));
        // System.out.println(tt.getNumberOfModels());
        // kb.listSentences();

        ttResult(tt.isEntailed(kb, d), tt.getNumberOfModels());
        // // // kb.listSentences();

        PLFCEntails fc = new PLFCEntails();

        fcResult(fc.isEntailed(kb, d), fc.getAgendaCatcher());

        // // PLBCEntails bc = new PLBCEntails();

        // // // bcResult(bc.isEntailed(kb, d), bc.getAgendaCatcher());

        PLBCEntails bc2 = new PLBCEntails();

        bcResult(bc2.TestRBC(kb, d), bc2.getAgendaCatcher());

        // System.out.println("Start");
        // for(int i = 0; i < finalListOfSentences.size(); i++){
        //     System.out.println(passedSentences.get(i));
        //     System.out.println(finalListOfSentences.get(i));

        //     System.out.println(finalListOfSentences.get(i).equals(passedSentences.get(i)));
        //     if(finalListOfSentences.get(i).hasConnective()){
        //         System.out.println(finalListOfSentences.get(i).getConnective().equals(passedSentences.get(i).getConnective()));
        //         System.out.println(finalListOfSentences.get(i).getSymbols().size() == passedSentences.get(i).getSymbols().size());
        //     }
        //     System.out.println(finalListOfSentences.get(i).getClass());
        //     System.out.println(passedSentences.get(i).getClass());
        // }
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
                if (agenda.indexOf(s) < agenda.size() -1){
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
                if (agenda.indexOf(s) < agenda.size() -1){
                    System.out.print(", ");
                }
            }
            System.out.println();
        } else {
            System.out.println("NO");
        }
    }

}
