import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<Sentence> finalListOfSentences = new ArrayList<Sentence>();

        SimpleSentence b = new SimpleSentence("b");
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

        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        sentences.add(p2);
        sentences.add(p3);
        ComplexSentence testComplexSentence = new ComplexSentence(sentences, "=>");
        finalListOfSentences.add(testComplexSentence);

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

        KnowledgeBase kb = new KnowledgeBase(finalListOfSentences);

        kb.listSymbols();
        kb.listSentences();

        TTEntails tt = new TTEntails();

        System.out.println("result");

        System.out.println(tt.isEntailed(kb, d));
        System.out.println(tt.getNumberOfModels());
        kb.listSentences();
        
    }
}
