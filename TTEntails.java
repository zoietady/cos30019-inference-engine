import java.util.ArrayList;

public class TTEntails {

    public TTEntails(){

    }

    public boolean isEntailed(KnowledgeBase kb, Sentence alpha) {
        ArrayList<String> symbols = new ArrayList<String>(alpha.getSymbols());
        symbols.addAll(kb.getSymbols());
        return ttCheckAll(kb, alpha, symbols, new Model());
    }

    public boolean ttCheckAll(KnowledgeBase kb, Sentence alpha,
            ArrayList<String> symbols, Model model) {
        if (symbols.isEmpty()) {
            if (model.isTrue(kb.asSingleSentence())) {
                return model.isTrue(alpha);
            } else {
                return true;
            }
        }

        String p = symbols.get(0);
        ArrayList<String> rest = symbols;
        rest.remove(0);
        System.out.println(p);
        System.out.println(rest);
        return ttCheckAll(kb, alpha, rest, model.union(p, true)) && ttCheckAll(kb, alpha, rest, model.union(p, false));
    }
}
