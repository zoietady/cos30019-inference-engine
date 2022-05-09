import java.util.HashSet;
import java.util.Set;

public class BinarySentence implements Sentence{
    private SimpleSentence x;
    private SimpleSentence y;
    private String connective;
    
    public BinarySentence(SimpleSentence x, SimpleSentence y, String connective) {
        this.x = x;
        this.y = y;
        this.connective = connective;
    }

    public SimpleSentence getX() {
        return x;
    }

    public SimpleSentence getY() {
        return y;
    }

    public String getConnective() {
        return connective;
    }

    @Override
    public Set<String> getSymbols() {
        Set<String> symbols = new HashSet<String>();
        symbols.addAll(x.getSymbols());
        symbols.addAll(y.getSymbols());
        return symbols;
    }

    @Override
    public String toString() {
        return "(" + x + " " + connective + " " + y + ")";
    }

    
    
}
