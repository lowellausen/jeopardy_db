package CPD.Adapter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import CPD.Models.*;

/**
 * Created by Leonardo on 09/06/2016.
 */
public class ReadTest {

    public static void ReadAll(){

        try
        {
            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            FileOutputStream fileDateTree = new FileOutputStream("dateTree.ser");
            FileOutputStream filePriceTree = new FileOutputStream("priceTree.ser");
            FileOutputStream fileShowTree = new FileOutputStream("showTree.ser");
            FileOutputStream fileRoundTree = new FileOutputStream("roundTree.ser");


            BPTree<Integer> dateTree = new BPTree<>();
            BPTree<Integer> priceTree = new BPTree<>();
            BPTree<Integer> shownumberTree = new BPTree<>();
            BPTree<String> roundTree = new BPTree<>();

            List<Integer> position = new ArrayList<>();

            boolean eof = false;

            while (!eof) {

                ObjectInputStream in = new ObjectInputStream(fileIn);
                Question question = null;
                question = (Question) in.readObject();
                if(!question.question.contentEquals("EOF")){

                    //Arquivo data
                    if (dateTree .get(question.airDate) != null){
                        position = dateTree .get(question.airDate);
                        position.add(question.index);
                    }
                    else{
                        List<Integer> array1 = new ArrayList<>();
                        dateTree .put(question.airDate, array1);
                        position = dateTree .get(question.airDate);
                        position.add(question.index);
                    }

                    //Arquivo valor
                    if (priceTree .get(question.value) != null){
                        position = priceTree .get(question.value);
                        position.add(question.index);
                    }
                    else{
                        List<Integer> array2 = new ArrayList<>();
                        priceTree .put(question.value, array2);
                        position = priceTree .get(question.value);
                        position.add(question.index);
                    }

                    //Arquivo show number
                    if (shownumberTree .get(question.showNumber) != null){
                        position = shownumberTree .get(question.showNumber);
                        position.add(question.index);
                    }
                    else{
                        List<Integer> array3 = new ArrayList<>();
                        shownumberTree .put(question.showNumber, array3);
                        position = shownumberTree .get(question.showNumber);
                        position.add(question.index);
                    }
                    //Arquivo Round
                    if (roundTree .get(question.round) != null){
                        position = roundTree .get(question.round);
                        position.add(question.index);
                    }
                    else{
                        List<Integer> array5 = new ArrayList<>();
                        roundTree .put(question.round, array5);
                        position = roundTree .get(question.round);
                        position.add(question.index);
                    }


                }
                else{
                    eof = true;
                }
            }
            fileIn.close();


            ObjectOutputStream outputDate = new ObjectOutputStream(fileDateTree);
            outputDate.writeObject(dateTree);
            outputDate.close();
            dateTree = null;
            System.gc();

            ObjectOutputStream outputPrice = new ObjectOutputStream(filePriceTree);
            outputPrice.writeObject(priceTree );
            outputPrice.close();
            priceTree = null;
            System.gc();


            ObjectOutputStream outputShow = new ObjectOutputStream(fileShowTree);
            outputShow.writeObject(shownumberTree );
            outputShow.close();
            shownumberTree = null;
            System.gc();

            ObjectOutputStream outputRound = new ObjectOutputStream(fileRoundTree);
            outputRound.writeObject(roundTree);
            outputRound.close();
            roundTree = null;
            System.gc();




        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
            return;
        }

    }

    public static void ReadOne (long index){

        try {
            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            fileIn.skip(index);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Question question = null;
            question = (Question) in.readObject();
            in.close();
            fileIn.close();

        }catch(IOException i){
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
            return;
        }

    }


    public static void MakeTrie(){

        try
        {
            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            FileOutputStream fileTrieTree = new FileOutputStream("trieTree.ser");



            Trie trieTree = new Trie();

            boolean eof = false;

            while (!eof) {

                ObjectInputStream in = new ObjectInputStream(fileIn);
                Question question = null;
                question = (Question) in.readObject();
                if(!question.question.contentEquals("EOF")){

                    //insere palavras na trie
                    String quest = question.question;
                    quest = quest.replaceAll("[^a-zA-Z ]","").toLowerCase();
                    String[] phrase = quest.split(" ");

                    for (String word: phrase){
                        if (!word.equals(""))
                            trieTree.addWord(word, question.index);
                    }
                }
                else{
                    eof = true;
                }
            }
            fileIn.close();


            ObjectOutputStream outputTrie = new ObjectOutputStream(fileTrieTree);
            outputTrie.writeObject(trieTree );
            outputTrie.close();
            fileTrieTree.close();



        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
            return;
        }

    }


}


