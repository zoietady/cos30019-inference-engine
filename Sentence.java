import java.util.Set;

public interface Sentence {
    Set<String> getSymbols();
    boolean contains(Sentence p);
    boolean hasConnective();
    String getConnective();
}
