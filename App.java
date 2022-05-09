import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        SimpleSentence testSentence1 = new SimpleSentence("s");

        SimpleSentence testSentence2 = new SimpleSentence("2");
        SimpleSentence testSentence3 = new SimpleSentence("3");

        // p&q => g
        //  p is a simple sentence
        //  q is simple 
        // p&q
        // p
        // q
        // & 
        // g
        // =>
        BinarySentence testBinarySentence = new BinarySentence(testSentence2, testSentence3, "&");

        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        sentences.add(testSentence1);
        sentences.add(testBinarySentence);


        ComplexSentence testComplexSentence = new ComplexSentence(sentences, "&");

        ArrayList<Sentence> finalListOfSentences = new ArrayList<Sentence>();
        finalListOfSentences.add(testSentence1);
        finalListOfSentences.add(testComplexSentence);

        KnowledgeBase kb = new KnowledgeBase(finalListOfSentences);

        kb.listSymbols();
        kb.listSentences();

        TTEntails tt = new TTEntails();
        tt.isEntailed(kb, testSentence1);
    }
}
