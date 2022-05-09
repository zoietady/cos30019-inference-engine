import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile {

    private String string;
    private String string2;
    private SimpleSentence s1;
    private SimpleSentence s2;
    private SimpleSentence s3;
    private ArrayList<Sentence> sentence;
    private ArrayList<Sentence> sentence2;

    private ComplexSentence testComplexSentence;
    private ComplexSentence testComplexSentence2;

    // creates empty knowledge base
    public ReadFile() {
    }

    public void readInput(BufferedReader r) {
        try {
            r.readLine();

            String tellString = r.readLine();
            tellString = tellString.replaceAll("\\s", "");
            String[] strings = tellString.split(";");
            String[] string2;

            // for (int i = 0; i < strings.length; i++) {

            // if (strings[i].contains("=>") || strings[i].contains("&")) {

            // for (String s : strings) {
            // if (s.matches("=>")) {
            // string = "=>";
            // }
            // }
            // for (String s : strings) {
            // if (s.matches("&")) {
            // string = "&";
            // }
            // }

            // string2 = strings[i].split("=>|&");
            // for (String s : string2) {
            // // System.out.println(s);
            // s1 = new SimpleSentence(s);
            // System.out.println(s1.toString());
            // }

            // } else {
            // System.out.println(strings[i]);
            // }

            // }
            for (int i = 0; i < strings.length; i++) {
                    if (strings[i].find("\\[.]")) {
                        s1 = new SimpleSentence(strings[i]);
                        System.out.println(s1.toString());
                    }
                    // if (strings[i].contains("[a-zA-Z0-9]")) {
                    // s3 = new SimpleSentence(strings[i]);
                    // sentence2.add(s3);
                    // }
                // if (strings[i].contains("=>")) {
                // string2 = strings[i];
                // testComplexSentence2 = new ComplexSentence(sentence2, string2);
                // }
                // System.out.println(testComplexSentence.toString());

                sentence.removeAll(sentence);
                sentence2.removeAll(sentence2);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(2);
        }
    }

    public ReadFile(BufferedReader r) {
        sentence = new ArrayList<Sentence>();
        sentence2 = new ArrayList<Sentence>();
        readInput(r);
    }
}