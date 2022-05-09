import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComplexSentence implements Sentence{
    private ArrayList<Sentence> sentences;
    private String connective;
    
    public ComplexSentence(ArrayList<Sentence> sentences, String connective) {
        this.sentences = sentences;
        this.connective = connective;
    }

    public ComplexSentence(Sentence sentence, String connective, Sentence anotherSentence) {
        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        sentences.add(sentence);
        sentences.add(anotherSentence);
        this.sentences = sentences;
        this.connective = connective;
    }

    public String getConnective() {
        return connective;
    }

    @Override
    public  Set<String> getSymbols() {
        Set<String> symbols = new HashSet<String>();
        for (Sentence sentence : sentences) {
            symbols.addAll(sentence.getSymbols());
        }
        return symbols;
    }

    @Override
    public String toString() {
        String s = "";

        for(int i = 0; i <= sentences.size() - 1; i++){
            s += sentences.get(i) + " ";
            if (i <= sentences.size() - 2){
                s += connective + " ";
            }
        }

        return s;
    }

    public Sentence getSimplerSentence(int i){
        return sentences.get(i);
    }

}
