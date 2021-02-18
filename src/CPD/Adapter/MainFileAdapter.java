package CPD.Adapter;


import CPD.Models.*;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * Created by Leonardo on 04/06/2016.
 */

public class MainFileAdapter {

    @SuppressWarnings("unchecked")
    public static void parseFile (File json){
        JSONParser parser = new JSONParser();




        try {

            Object obj = parser.parse(new FileReader(json));

            FileOutputStream fileOut = new FileOutputStream("mainFile.ser");
            FileOutputStream fileCategoryTree = new FileOutputStream("categoryTree.ser");


            BPTree<String> categoryTree = new BPTree<>();
            List<Integer> position = new ArrayList<>();





            JSONArray mainArray = (JSONArray) obj;

            Iterator<JSONObject> jsonObject = mainArray.iterator();
            while (jsonObject.hasNext()) {

                JSONObject current = jsonObject.next();

                String category = (String) current.get("category");
                String date = (String) current.get("air_date");
                String questionF = (String) current.get("question");
                String answer = (String) current.get("answer");
                String round =  (String) current.get("round");
                String number = (String) current.get("show_number");
                String value = (String) current.get("value");


                date = date.replace("-","");
                int dateInt = Integer.parseInt(date);
                int numberInt = Integer.parseInt(number);
                if (value!=null) {
                    value = value.replace("$", "").replace(",", "");
                }
                else{
                    value = "0";
                }
                int valueInt = Integer.parseInt(value);

                questionF = questionF.toLowerCase();
                category = category.toLowerCase();
                round = round.toLowerCase();
                answer = answer.toLowerCase();

                if (!HasMedia(questionF)){  //mata as questões que tem imagens etc
                    FileChannel c1;
                    long pos;

                    c1 = fileOut.getChannel();
                    pos = c1.position();

                    Question question = new Question ((int)pos,numberInt, dateInt, valueInt, category, questionF, answer, round);
                    ObjectOutputStream output = new ObjectOutputStream(fileOut);
                    output.writeObject(question);


                    //Arquivo categoria
                    if (categoryTree .get(question.category) != null){
                        position =categoryTree .get(question.category);
                        position.add(question.index);
                    }
                    else {
                        List<Integer> array = new ArrayList<>();
                        categoryTree.put(question.category, array);
                        position = categoryTree.get(question.category);
                        position.add(question.index);
                    }

                }
                  //
                    //output.close();
            }

            Question question = new Question (0, 0, 0, 0, null, "EOF", null, null);
            ObjectOutputStream output = new ObjectOutputStream(fileOut);
            output.writeObject(question);
            fileOut.close();

            ObjectOutputStream outputCategory = new ObjectOutputStream(fileCategoryTree);
            outputCategory.writeObject(categoryTree );
            outputCategory.close();



           // System.out.println(trieTree.containsWord("galileo"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static boolean HasMedia (String question){

        for (int i = 0; i < question.length(); i++) {
            if (question.charAt(i) == '<') {
                return true;
            }
        }
        return false;
    }

}
