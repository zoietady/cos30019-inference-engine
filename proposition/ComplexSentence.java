package proposition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Specification of complex sentence objects in the context of propositional logic.
 * 
 * Note: this is not an accurate representation of a complex sentenece in the context of any defined semantices for logical representations
 * Although it does embody what some  knowledge representation language  would refer to as a complex sentence, it behaves more closer
 * to that of a binarysentence that a true complex sentence
 * 
 * A complex sentence in this context is a sentence that contains a multiple sentences, connected by an operator
 * 
 * e.g. the complex sentence a&b&c&e&a is converted to sentence = ((a & (b&c)) & (e&a)), which is composed of sentence (a & (b&c)) and (e&a), and the connective &
 * where the complex sentence (a & (b&c)) is composed of a and (b&c) and a connective &. The same logic applies for the remaining complex sentences (b&c) and (e&a).
 * This recurisve structural behaviour or nature allows for a more convinent formulation or implementation of the different inference methods.
 * 
 * @author Zoie Tad-y 
 */
public class ComplexSentence implements Sentence{
    // contains sub sentences
    private ArrayList<Sentence> sentences;
    // joined by a connective
    private String connective;
    
    /**
     * constrcutor for ComplexSentence
     * @param sentences list of sentences in teh ComplexSentence
     * @param connective connective of the ComplexSentence
     */
    public ComplexSentence(ArrayList<Sentence> sentences, String connective) {
        this.sentences = sentences;
        this.connective = connective;
    }

    /**
     * separate constructor, where 2 single sentences are provides and a connective.
     * @param sentence first sentence
     * @param connective conncetive of ComplexSentence
     * @param anotherSentence second sentence
     */
    public ComplexSentence(Sentence sentence, String connective, Sentence anotherSentence) {
        //create a list of the two sentences
        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        sentences.add(sentence);
        sentences.add(anotherSentence);

        // assign this list to the sentences property
        this.sentences = sentences;
        this.connective = connective;
    }

    /**
     * getter for connectives
     * 
     * @return connective of sentence
     */
    public String getConnective() {
        return connective;
    }

    /**
     * getter for symbols in the complex sentence
     * 
     * @return set of ssymbols
     */
    @Override
    public  Set<String> getSymbols() {
        Set<String> symbols = new HashSet<String>();
        // for each sentence 
        for (Sentence sentence : sentences) {
            // get their symbols and add it to the set
            symbols.addAll(sentence.getSymbols());
        }
        // return set
        return symbols;
    }

    /**
     * string rep of the complex sentence
     * @return String rep of sentence
     */
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

    /**
     * get a "sub" sentence within the complex sentence
     * @param i index of sentence
     * @return subsentence within the complex sentence
     */
    public Sentence getSimplerSentence(int i){
        return sentences.get(i);
    }

    /**
     * check if the sentence contains a given sentence
     * @param p sentence to check if its within this complex sentence
     * @return bool value if its wihtin the sentence
     */
    public boolean contains(Sentence p){
        return sentences.contains(p);
    }

    /**
     * hashcode rep of the complex sentence, used for maps
     */
    @Override
    public int hashCode() {
        return sentences.hashCode() + connective.hashCode();
    }

    /**
     * user fo assessing for object equivalence
     */
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

    // true, if it contains a connective 
    @Override
    public boolean hasConnective() {
        return getConnective() != null;
    }
}
