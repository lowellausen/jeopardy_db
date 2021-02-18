package CPD.Adapter;

import CPD.Models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Isadora on 20/06/2016.
 */
public class Sort {

    public static List<Question> sort(boolean invert, boolean category, boolean date, boolean round, boolean show, boolean price, List<Question> questions) {
        List<Question> questions2 = null;
        try {

            if (category ) {
                questions2 = sortCat(questions);
            }
            else if (date) {
                questions2 = sortDate(questions);


            } else if (round ) {
                questions2 = sortRound(questions);

            } else if (show ) {
                questions2 = sortShow(questions);

            } else if (price ) {
                questions2 = sortPrice(questions);
            }
            if(invert){
                questions2 = invert(questions2);
            }


        }catch(Exception e){
            e.printStackTrace();

        }
        return questions2;
    }

    public static List<Question> sortDate(List<Question> input){
        List<Question> list = null;
        if(input.size() <= 1){
            return input;
        }

        int middle = (int) Math.ceil((double)input.size() / 2);
        Question pivot = input.get(middle);

        List<Question> less = new ArrayList<Question>();
        List<Question> greater = new ArrayList<Question>();

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).airDate <= pivot.airDate){
                if(i == middle){
                    continue;
                }
                less.add(input.get(i));
            }
            else{
                greater.add(input.get(i));
            }
        }
        list = concatenateDate(sortDate(less), pivot, sortDate(greater));

        return list;
    }

    public static List<Question> concatenateDate(List<Question> less, Question pivot, List<Question> greater){

        List<Question> list = new ArrayList<Question>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }


    public static List<Question> sortCat(List<Question> input){
        List<Question> list = null;
        if(input.size() <= 1){
            return input;
        }

        int middle = (int) Math.ceil((double)input.size() / 2);
        Question pivot = input.get(middle);

        List<Question> less = new ArrayList<Question>();
        List<Question> greater = new ArrayList<Question>();

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).category.compareTo(pivot.category) <= 0 ){
                if(i == middle){
                    continue;
                }
                less.add(input.get(i));
            }
            else{
                greater.add(input.get(i));
            }
        }
        list = concatenateCat(sortCat(less), pivot, sortCat(greater));

        return list;
    }

    public static List<Question> concatenateCat(List<Question> less, Question pivot, List<Question> greater){

        List<Question> list = new ArrayList<Question>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }

    public static List<Question> sortRound(List<Question> input){
        List<Question> list = null;
        if(input.size() <= 1){
            return input;
        }

        int middle = (int) Math.ceil((double)input.size() / 2);
        Question pivot = input.get(middle);

        List<Question> less = new ArrayList<Question>();
        List<Question> greater = new ArrayList<Question>();

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).round.compareTo(pivot.round) <= 0 ){
                if(i == middle){
                    continue;
                }
                less.add(input.get(i));
            }
            else{
                greater.add(input.get(i));
            }
        }
        list = concatenateRound(sortRound(less), pivot, sortRound(greater));

        return list;
    }

    public static List<Question> concatenateRound(List<Question> less, Question pivot, List<Question> greater){

        List<Question> list = new ArrayList<Question>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }

    public static List<Question> sortPrice(List<Question> input){
        List<Question> list = null;
        if(input.size() <= 1){
            return input;
        }

        int middle = (int) Math.ceil((double)input.size() / 2);
        Question pivot = input.get(middle);

        List<Question> less = new ArrayList<Question>();
        List<Question> greater = new ArrayList<Question>();

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).value <= pivot.value){
                if(i == middle){
                    continue;
                }
                less.add(input.get(i));
            }
            else{
                greater.add(input.get(i));
            }
        }
        list = concatenatePrice(sortPrice(less), pivot, sortPrice(greater));

        return list;
    }

    public static List<Question> concatenatePrice(List<Question> less, Question pivot, List<Question> greater){

        List<Question> list = new ArrayList<Question>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }


    public static List<Question> sortShow(List<Question> input){
        List<Question> list = null;
        if(input.size() <= 1){
            return input;
        }

        int middle = (int) Math.ceil((double)input.size() / 2);
        Question pivot = input.get(middle);

        List<Question> less = new ArrayList<Question>();
        List<Question> greater = new ArrayList<Question>();

        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).airDate <= pivot.airDate){
                if(i == middle){
                    continue;
                }
                less.add(input.get(i));
            }
            else{
                greater.add(input.get(i));
            }
        }
        list = concatenateShow(sortShow(less), pivot, sortShow(greater));

        return list;
    }

    public static List<Question> concatenateShow(List<Question> less, Question pivot, List<Question> greater){

        List<Question> list = new ArrayList<Question>();

        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }

        list.add(pivot);

        for (int i = 0; i < greater.size(); i++) {
            list.add(greater.get(i));
        }

        return list;
    }

    public static List<Question> invert(List<Question> questions){
        Collections.reverse(questions);
        return questions;
    }


}












