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

    public boolean contains(Sentence p){
        return sentences.contains(p);
    }

    @Override
    public int hashCode() {
        return sentences.hashCode() + connective.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComplexSentence other = (ComplexSentence) obj;
        if (connective == null) {
            if (other.connective != null)
                return false;
        } else if (!connective.equals(other.connective))
            return false;
        if (sentences == null) {
            if (other.sentences != null)
                return false;
        } else if (!sentences.equals(other.sentences))
            return false;
        return true;
    }

    @Override
    public boolean hasConnective() {
        return getConnective() != null;
    }

    
}
