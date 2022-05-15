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
    private SimpleSentence ss5;
    private ComplexSentence cs1;
    private ComplexSentence cs2;
    private ComplexSentence cs3;

    private String[] s2;
    private String[] s3;

    private String con1;
    private String con2;
    private String[] string2;

    String[] temp;

    private String[] splitString;
    private ArrayList<Sentence> listOfSS1;
    private ArrayList<Sentence> listOfSS2;
    private ArrayList<Sentence> listOfSS3;
    private ArrayList<Sentence> listComplex1;
    private ArrayList<Sentence> listComplex2;
    private ArrayList<Sentence> listComplex3;
    private ArrayList<Sentence> tempList;

    // creates empty knowledge base
    public ReadFile() {
        // listComplex1 = new ArrayList<Sentence>();
        // listComplex2 = new ArrayList<Sentence>();
        // listComplex3 = new ArrayList<Sentence>();
    }

    public void readInput(BufferedReader r) {
        try {
            r.readLine();
            String tellString = r.readLine();
            tellString = tellString.replaceAll("\\s", "");
            String[] strings = tellString.split(";");
            listComplex1 = new ArrayList<Sentence>();
            listComplex2 = new ArrayList<Sentence>();
            listComplex3 = new ArrayList<Sentence>();
            for (String s : strings) {
                if (!s.contains("=>") && !s.contains("&")) {
                    listOfSS1 = new ArrayList<Sentence>();
                    ss1 = new SimpleSentence(s);
                    con1 = "";
                    listOfSS1.add(ss1);
                    cs1 = new ComplexSentence(listOfSS1, con1);
                    listComplex1.add(cs1);
                }
                if (s.contains("=>")) {
                    listOfSS1 = new ArrayList<Sentence>();
                    s2 = s.split("=>");
                    ss2 = new SimpleSentence(s2[1]);
                    con1 = "=>";
                    if (!s2[0].contains("&")) {
                        ss1 = new SimpleSentence(s2[0]);
                        listOfSS1.add(ss1);
                        listOfSS1.add(ss2);
                    }
                    cs1 = new ComplexSentence(listOfSS1, con1);
                    listComplex1.add(cs1);
                    if (s2[0].contains("&")) {
                        listOfSS2 = new ArrayList<Sentence>();
                        tempList = new ArrayList<Sentence>();
                        s2[0].replaceAll("\\s", "");
                        s3 = s2[0].split("&");
                        ss3 = new SimpleSentence(s3[0]);
                        ss4 = new SimpleSentence(s3[1]);
                        listOfSS2.add(ss3);
                        listOfSS2.add(ss4);
                        if(s3.length > 2){
                            ss5 = new SimpleSentence(s3[2]);
                            listOfSS2.add(ss5);
                        }
                        con2 = "&";
                        cs3 = new ComplexSentence(listOfSS2, con2);
                        tempList.add(cs3);
                        tempList.add(ss2);
                        cs2 = new ComplexSentence(tempList, con1);
                        listComplex1.add(cs2);
                    }
                }
            }
            System.out.println(listComplex1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(2);
        }
    }
    public ReadFile(BufferedReader r) {
        readInput(r);
    }
}