import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class ReadFile {

    private SimpleSentence s1;
    private SimpleSentence s2;
    private SimpleSentence s3;
    private ComplexSentence cs1;
    private ComplexSentence cs2;
    private String con1;
    private String con2;
    private String[] string2;

    String[] temp;

    private String[] splitString;
    private ArrayList<Sentence> list;
    private ArrayList<Sentence> listComplex;

    private Map<String, SimpleSentence> hashMap = new HashMap<String, SimpleSentence>();

    // creates empty knowledge base
    public ReadFile() {

    }

    public void readInput(BufferedReader r) {

        try {
            r.readLine();
            String tellString = r.readLine();
            tellString = tellString.replaceAll("\\s", "");
            String[] strings = tellString.split(";");
            
            
            // for (int i = 0; i < strings.length; i++) {
            //     string2 = strings[i].split("=>|&");
            //     for (String s : string2) {
            //         s1 = new SimpleSentence(s);
            //     System.out.println(hashMap);

            //         hashMap.put(s, s1);
            //     }
            // }

            for(int i = 0; i < strings.length; i++){
                
                
                for(String s : temp){
                    if( s.contains("=>")){
                        s3 = new SimpleSentence(temp[0]);
                        list.add(s3);
                        con2 = "=>";
                        cs2 = new ComplexSentence(list, con2);
                        if(strings[i].contains("&")){
                            list.clear();
                            temp = strings[i].split("&");
                            s1 = new SimpleSentence(temp[0]);
                            list.add(s1);
                            s2 = new SimpleSentence(temp[1]);
                            con1 = "=>";
                            list.add(s2);
                            
                            cs1 = new ComplexSentence(list, con1);
                        }
                        
                    }
                }


                System.out.println(hashMap);
            }

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