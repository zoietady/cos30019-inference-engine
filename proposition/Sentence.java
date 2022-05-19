package proposition;
import java.util.Set;

/**
 * Specification of sentence objects in the context of propositional logic
 * 
 * @author Zoie Tad-y 
 */
public interface Sentence {
    // contains a propositional symbol or symbols
    Set<String> getSymbols();
    boolean contains(Sentence p);
    // may have a connective 
    boolean hasConnective();
    String getConnective();
}
