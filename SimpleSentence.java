import java.util.HashSet;
import java.util.Set;

public class SimpleSentence implements Sentence{
    private String symbol;

    public SimpleSentence(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public Set<String> getSymbols() {
        Set<String> symbols = new HashSet<String>();
        symbols.add(getSymbol());
        return symbols;
    }

    @Override
    public String toString() {
        return "(" + symbol + ")";
    }

    
}
