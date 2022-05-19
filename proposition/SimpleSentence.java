package proposition;
import java.util.HashSet;
import java.util.Set;


/**
 * Specification of simple sentence objects in the context of propositional logic.
 * 
 * Note: this is not an accurate representation of a simple sentenece in the context of any defined semantices for logical representations
 * This data structure defines sentences as what would simply be known as propositional symbols in more common knowledge representation languages.
 * 
 * A simple sentence in this context is a sentence that contains a single propositional symbol, without any other operators or connectives.
 * 
 * @author Zoie Tad-y 
 */
public class SimpleSentence implements Sentence{
    // propositional symbol
    private String symbol;

    /**
     * constructor for porpositional symbol
     * @param symbol - string symbol
     */
    public SimpleSentence(String symbol) {
        this.symbol = symbol;
    }

    /**
     * getter for symbol
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * implementation of method specfication in sentence interface
     * user for different inference methods
     */
    @Override
    public Set<String> getSymbols() {
        Set<String> symbols = new HashSet<String>();
        symbols.add(getSymbol());
        return symbols;
    }

    /**
     * converts sympol sentence as a string object
     */
    @Override
    public String toString() {
        return symbol;
    }

    /**
     * checks if sentence contains said symbol
     * @return true if this sentence contains symbol
     */
    public boolean contains(Sentence p){
        if (p instanceof ComplexSentence){
            return false;
        }
        SimpleSentence ss = (SimpleSentence) p;
        return ss.getSymbol() == this.symbol;
    }

    /**
     * user for hash maps
     * 
     * @return int hash rep of object
     */
    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    /**
     * used for assess object equivalences
     * 
     * @return true if equivalent to given object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimpleSentence other = (SimpleSentence) obj;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        return true;
    }

    /**
     * simple sentences does not contain any connectives in this specification, hence always true
     * @return false
     */
    @Override
    public boolean hasConnective() {
        return false;
    }

    /**
     * simple sentences does not contain any connectives in this specification, hence always null
     * @return nul
     */
    @Override
    public String getConnective() {
        return null;
    }

    

}
