package CPD.Models;
/**
 * Created by Leonardo on 03/06/2016.
 */

public class Question implements java.io.Serializable {
    public int index;
    public int showNumber;
    public int airDate;
    public int value;
    public String category;
    public String question;
    public String answer;
    public String round;

    public Question (int index ,int showNumber, int airDate, int value, String category, String question, String answer, String round){
        this.index = index;
        this.showNumber = showNumber;
        this.airDate = airDate;
        this.value = value;
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.round = round;
    }
}