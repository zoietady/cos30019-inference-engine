import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TTEntails {

    public TTEntails() {

    }

    private int numberOfModels;

    public boolean isEntailed(KnowledgeBase kb, Sentence alpha) {
        Set<String> setSymbols = new HashSet<String>(alpha.getSymbols());
        setSymbols.addAll(kb.getSymbols());
        List<String> symbols = new ArrayList<String>(setSymbols);
        numberOfModels = 0;
        return ttCheckAll(kb, alpha, symbols, new Model());
    }

    public boolean ttCheckAll(KnowledgeBase kb, Sentence alpha, List<String> symbols, Model model) {
        if (symbols.isEmpty()) {
            if (model.isTrue(kb.asSingleSentence())) {
                System.out.println("KB is true");
                return model.isTrue(alpha);
            } else {
                return true;
            }
        }

        // System.out.println("what's in symbols");
        // for (String a : symbols) {
        //     System.out.println(a);
        // }
        // System.out.println("=====");

        String p = symbols.get(0);
        List<String> rest = symbols.subList(1, symbols.size());

        // System.out.println("p = " + p);
        // System.out.println("what's in rest");
        // for (String a : rest) {
        //     System.out.println(a);
        // }
        // System.out.println("=====");

        return ttCheckAll(kb, alpha, rest, model.union(p, true)) && ttCheckAll(kb, alpha, rest, model.union(p, false));
    }

    public int getNumberOfModels() {
        return this.numberOfModels;
    }

    private void incrementModel() {
        this.numberOfModels++;
    }
}
