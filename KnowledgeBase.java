import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KnowledgeBase {
    private ArrayList<Sentence> sentences;
    private Set<String> symbols;

    public KnowledgeBase(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
        this.symbols = gatherSymbols();
    }


    public boolean addSentence(Sentence sentence){
        boolean result = this.sentences.add(sentence);
        this.symbols.addAll(sentence.getSymbols());
        return result;
    }

    public boolean addSentences(ArrayList<Sentence> sentences){
        boolean result = this.sentences.addAll(sentences);
        this.symbols = gatherSymbols();
        return result;
    }

    public Set<String> gatherSymbols(){
        Set<String> symbols =  new HashSet<String>();

        for (Sentence sentence : this.sentences) {
            symbols.addAll(sentence.getSymbols());
        }
        return symbols;
    }

    public void listSymbols(){
        System.out.println("Propositional Symbols");
        for (String symbol : symbols) {
            System.out.println(symbol);
        }
    }

    public void listSentences(){
        System.out.println("Sentences");
        for (Sentence sentence : sentences) {
            System.out.println(sentence.toString());
        }
    }

    public Set<String> getSymbols(){
        return this.symbols;
    }

    public ArrayList<Sentence> getSentences(){
        return this.sentences;
    }


    public Sentence asSingleSentence() {
        Sentence kbInSingleSentence =  new ComplexSentence(sentences, "&");
        return kbInSingleSentence;
    }

    

}
