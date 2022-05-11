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
        return symbol;
    }

    public boolean contains(Sentence p){
        if (p instanceof ComplexSentence){
            return false;
        }
        SimpleSentence ss = (SimpleSentence) p;
        return ss.getSymbol() == this.symbol;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

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

    @Override
    public boolean hasConnective() {
        return false;
    }

    @Override
    public String getConnective() {
        return null;
    }

    

}
