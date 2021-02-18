package CPD.Adapter;
import CPD.Models.*;
import CPD.Models.BPTree.Node;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * Created by Isadora on 17/06/2016.
 */
public class Search {

    public static List<Question> Search(String cat, int dateS, int dateF, String round, int show, int price, String word) {
        List<Question> questionsList = new ArrayList<>();
        try {
            if (word != null) {
                questionsList = SearchTrie(word);

                if (cat != null){
                    questionsList = SearchCat(cat, questionsList);
                }

                if (dateS != -1 && dateF != -1) {
                    questionsList = SearchDate(dateS, dateF, questionsList);

                }
                if (show != -1) {
                    questionsList = SearchShow(show, questionsList);

                }
                if (round != null) {
                    questionsList = SearchRound(round, questionsList);

                }
                if (price != -1) {
                    questionsList = SearchPrice(price, questionsList);

                }

            }else if (cat != null) {
                questionsList = SearchCatFirst(cat, questionsList);
                if (dateS != -1 && dateF != -1) {
                    questionsList = SearchDate(dateS, dateF, questionsList);

                }
                if (show != -1) {
                    questionsList = SearchShow(show, questionsList);

                }
                if (round != null) {
                    questionsList = SearchRound(round, questionsList);

                }
                if (price != -1) {
                    questionsList = SearchPrice(price, questionsList);

                }
            }
            else if (dateS != -1 && dateF != -1){
                questionsList = SearchDateFirst(dateS, dateF, questionsList);
                if (show != -1) {
                    questionsList = SearchShow(show, questionsList);

                }
                if (round != null) {
                    questionsList = SearchRound(round, questionsList);

                }
                if (price != -1) {
                    questionsList = SearchPrice(price, questionsList);

                }
            }
            else if (round != null){
                questionsList = SearchRoundFirst(round, questionsList);
                if (show != -1) {
                    questionsList = SearchShow(show, questionsList);

                }
                if (price != -1) {
                    questionsList = SearchPrice(price, questionsList);

                }
            }
            else if (show != -1){
                questionsList = SearchShowFirst(show,  questionsList);
                if (price != -1) {
                    questionsList = SearchPrice(price, questionsList);

                }
            }
            else if (price != -1){
                questionsList = SearchPriceFirst(price, questionsList);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        return questionsList;
    }

    public static List<Question> SearchDate(int dateS, int dateF, List<Question> questionList){
        List<Question> questions = new ArrayList<>();
        for (Question question: questionList){
            if (question.airDate > dateS && question.airDate < dateF){
                questions.add(question);
            }
        }
        return questions;
    }

    public static List<Question> SearchRound(String round, List<Question> questionList){
        List<Question> questions = new ArrayList<>();
        for (Question question: questionList){
            if (question.round.equals(round)){
                questions.add(question);
            }
        }
        return questions;
    }

    public static List<Question> SearchPrice(int price, List<Question> questionList){
        List<Question> questions = new ArrayList<>();
        for (Question question: questionList){
            if (question.value == price){
                questions.add(question);
            }
        }
        return questions;
    }

    public static List<Question> SearchCat(String category, List<Question> questionList){
        List<Question> questions = new ArrayList<>();
        for (Question question: questionList){
            if (question.category.equals(category)){
                questions.add(question);
            }
        }
        return questions;
    }

    public static List<Question> SearchShow(int show, List<Question> questionList){
        List<Question> questions = new ArrayList<>();
        for (Question question: questionList){
            if (question.showNumber == show){
                questions.add(question);
            }
        }
        return questions;
    }

    public static  List<Question> SearchTrie (String word){
        List<Question> list = new ArrayList<>();
        try {
            FileInputStream fileTrie = new FileInputStream("trieTree.ser");
            ObjectInputStream in = new ObjectInputStream(fileTrie);
            Trie tree = null;
            tree = (Trie) in.readObject();
            in.close();
            fileTrie.close();

            List<Integer> bucket = tree.returnBucket(tree.raiz,word);



            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            for (int pos : bucket){
                Question question = null;
                fileIn.skip(pos);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                question = (Question) input.readObject();
                list.add(question);
                fileIn.getChannel().position(0);
            }
            fileIn.close();
        }catch(IOException i){
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
        }
        return list;
    }

    public static List<Question> SearchCatFirst(String cat, List<Question> questionsList) {
        List<Integer> position;
        try {
            FileInputStream fileCat = new FileInputStream("categoryTree.ser");
            ObjectInputStream in = new ObjectInputStream(fileCat);
            BPTree tree = null;
            tree = (BPTree) in.readObject();
            position = tree.search(tree.root, cat, tree.height);

            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            for (int pos : position) {
                Question question = null;
                fileIn.skip(pos);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                question = (Question) input.readObject();
                questionsList.add(question);
                fileIn.getChannel().position(0);
            }
            fileCat.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return questionsList;
    }

    public static List<Question> SearchDateFirst(int dateS, int dateF, List<Question> questionsList) {
        List<Integer> position = new ArrayList<>();
        try {
            FileInputStream fileDate = new FileInputStream("dateTree.ser");
            ObjectInputStream in = new ObjectInputStream(fileDate);
            BPTree tree = null;
            tree = (BPTree) in.readObject();
            position = SearchDateRange(dateS, dateF, tree.root, tree.height, position);

            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            for (int pos : position) {
                Question question = null;
                fileIn.skip(pos);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                question = (Question) input.readObject();
                questionsList.add(question);
                fileIn.getChannel().position(0);
            }
            fileIn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionsList;
    }

    public static List<Integer> SearchDateRange(int dateS, int dateF, Node h, int ht, List<Integer> position) {
        StringBuilder s = new StringBuilder();
        BPTree.Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                if (children[j].key.compareTo(dateS) >= 0 && children[j].key.compareTo(dateF) <= 0) {
                    for (int pos : children[j].val) {
                        position.add(pos);
                    }
                }
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                SearchDateRange(dateS, dateF, children[j].next, ht - 1, position);
            }
        }

        return position;

    }


    public static List<Question> SearchRoundFirst(String round, List<Question> questionsList) {
        List<Integer> position;
        try {
            FileInputStream fileRound = new FileInputStream("roundTree.ser");
            ObjectInputStream in = new ObjectInputStream(fileRound);
            BPTree tree = null;
            tree = (BPTree) in.readObject();
            position = tree.search(tree.root, round, tree.height);

            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            for (int pos : position) {
                Question question = null;
                fileIn.skip(pos);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                question = (Question) input.readObject();
                questionsList.add(question);
                fileIn.getChannel().position(0);
            }
            fileIn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionsList;
    }


    public static List<Question> SearchPriceFirst(int price,  List<Question> questionsList) {
        List<Integer> position;
        try {
            FileInputStream filePrice = new FileInputStream("priceTree.ser");
            ObjectInputStream in = new ObjectInputStream(filePrice);
            BPTree tree = null;
            tree = (BPTree) in.readObject();
            position = tree.search(tree.root, price, tree.height);

            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            for (int pos : position) {
                Question question = null;
                fileIn.skip(pos);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                question = (Question) input.readObject();
                questionsList.add(question);
                fileIn.getChannel().position(0);
            }
            fileIn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionsList;
    }

    public static List<Question> SearchShowFirst(int show, List<Question> questionsList) {
        List<Integer> position;
        try {
            FileInputStream fileShow = new FileInputStream("showTree.ser");
            ObjectInputStream in = new ObjectInputStream(fileShow);
            BPTree tree = null;
            tree = (BPTree) in.readObject();
            position = tree.search(tree.root, show, tree.height);

            FileInputStream fileIn = new FileInputStream("mainFile.ser");
            for (int pos : position) {
                Question question = null;
                fileIn.skip(pos);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                question = (Question) input.readObject();
                questionsList.add(question);
                fileIn.getChannel().position(0);
            }
            fileIn.close();

        } catch (Exception e) {
           e.printStackTrace();
        }
        return questionsList;
    }




}









