import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		passModel.assignments.put(symbol, b);
        System.out.println("xxxxxx");
        for (Entry<String, Boolean> entry : assignments.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("yyyyyyy");

		return passModel;
	}

    public boolean evaluate(Sentence s){
        if (s instanceof SimpleSentence){
            return assignments.get(s);
        }

        if(s instanceof ComplexSentence){
            return evaluate((ComplexSentence) s);
        }

        return false;
    }

    public boolean evaluate(ComplexSentence s){
        Boolean firstValue = evaluate(s.getSimplerSentence(0));
		Boolean secondValue = evaluate(s.getSimplerSentence(1));
		boolean bothValuesKnown = firstValue != null && secondValue != null;
		String connective = s.getConnective();

		if (connective.equals("&")) {
			return Boolean.FALSE.equals(firstValue) || Boolean.FALSE.equals(secondValue) ? Boolean.FALSE : (bothValuesKnown ? Boolean.TRUE : false);
		} else if (connective.equals("=>")) {
			return Boolean.FALSE.equals(firstValue) || Boolean.TRUE.equals(secondValue) ? Boolean.TRUE : (bothValuesKnown ? Boolean.FALSE : false);
		}

		return false;
    }
}
