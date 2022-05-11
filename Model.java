import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Boolean> assignments = new HashMap<String, Boolean>();

    public Model() {
    }

    public boolean isTrue(String symbol) {
        return Boolean.TRUE.equals(assignments.get(symbol));
    }

    public boolean isTrue(Sentence s) {
        return Boolean.TRUE.equals(evaluate(s));
    }

    public Model union(String symbol, boolean b) {
        Model passModel = new Model();
        passModel.assignments.putAll(this.assignments);
        passModel.assignments.put(symbol, b); // add symbol and true or false

        // System.out.println(symbol);

        // System.out.println("XXXXX");

        // for (Entry<String, Boolean> i : passModel.assignments.entrySet()) {
        // System.out.println(i.getKey() + " " + i.getValue());
        // }
        // System.out.println("YYYYYY");

        return passModel;
    }

    public boolean evaluate(Sentence s) {
        if (s instanceof SimpleSentence) {
            SimpleSentence ss = (SimpleSentence) s;
            // System.out.println("evaluates");
            // System.out.println(ss);
            // System.out.println("--------");
            // for (Entry<String, Boolean> i : assignments.entrySet()) {
            // System.out.println(i.getKey() + " " + i.getValue());
            // }
            // System.out.println("--------");
            return assignments.get(ss.getSymbol());
        }

        if (s instanceof ComplexSentence) {
            return evaluate((ComplexSentence) s);
        }

        return false;
    }

    public boolean evaluate(ComplexSentence s) {
        Boolean firstValue = evaluate(s.getSimplerSentence(0));
        Boolean secondValue = evaluate(s.getSimplerSentence(1));
        // boolean bothValuesKnown = firstValue != null && secondValue != null;

        // System.out.println();
        // System.out.print(firstValue == null);
        // System.out.print(secondValue == null);
        // System.out.println();

        String connective = s.getConnective();

        if (connective.equals("&")) {
            return Boolean.FALSE.equals(firstValue) || Boolean.FALSE.equals(secondValue) ? Boolean.FALSE : Boolean.TRUE;
        } else if (connective.equals("=>")) {
            return Boolean.FALSE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : Boolean.FALSE;
        }

        if (connective.equals("&")) {
			return Boolean.FALSE.equals(firstValue) || Boolean.FALSE.equals(secondValue) ? Boolean.FALSE :  Boolean.TRUE;
		} else if (connective.equals("||")) {
			return Boolean.TRUE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : Boolean.FALSE;
		} else if (connective.equals("=>")) {
			return Boolean.FALSE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : Boolean.FALSE;
		} else if (connective.equals("<==>")) {
			return firstValue.equals(secondValue);
		}

        return false;
    }
}
