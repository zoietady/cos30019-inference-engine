import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.LimitExceededException;

import java.util.HashMap;

public class ReadFile {

    private SimpleSentence ss1;
    private SimpleSentence ss2;
    private SimpleSentence ss3;
    private SimpleSentence ss4;
    private SimpleSentence ss_lone;
    private ComplexSentence cs1;
    private ComplexSentence cs2;

    private String[] s2;

    private String con1;
    private String con2;
    private String[] string2;

    String[] temp;

    private String[] splitString;
    private ArrayList<Sentence> listOfSS1;
    private ArrayList<Sentence> listOfSS2;
    private ArrayList<Sentence> listComplex;

    private Map<String, SimpleSentence> hashMap = new HashMap<String, SimpleSentence>();

    // creates empty knowledge base
    public ReadFile() {

        listComplex = new ArrayList<Sentence>();
    }

    public void readInput(BufferedReader r) {
        try {
            r.readLine();
            String tellString = r.readLine();
            tellString = tellString.replaceAll("\\s", "");
            String[] strings = tellString.split(";");
            listComplex = new ArrayList<>();
            for (String s : strings) {
                if (s.contains("=>")) {
                    listOfSS1 = new ArrayList<Sentence>();
                    s2 = s.split("=>");
                    ss3 = new SimpleSentence(s2[1]);
                    con1 = "=>";
                    listOfSS1.add(ss3);
                    cs1 = new ComplexSentence(listOfSS1, con1);
                }
                if (s2[0].contains("&")) {
                    listOfSS2 = new ArrayList<Sentence>();
                    s2 = s.split("&");
                    con2 = "&";
                    ss2 = new SimpleSentence(s2[0]);
                    ss3 = new SimpleSentence(s2[1]);
                    listOfSS2.add(ss2);
                    listOfSS2.add(ss3);
                    cs1 = new ComplexSentence(listOfSS2, con2);
                }
                listComplex.add(cs1);
                listComplex.add(cs2);


            }
            System.out.println(listComplex.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(2);
        }
    }

    public ReadFile(BufferedReader r) {
        // sentence = new ArrayList<Sentence>();
        // sentence2 = new ArrayList<Sentence>();
        readInput(r);
    }
}