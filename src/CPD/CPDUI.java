package CPD;


import CPD.Adapter.*;
import CPD.Models.ButtonColumn;
import CPD.Models.Question;
import CPD.Models.Table;
import CPD.utils.IOUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by Leonardo on 04/06/2016.
 */
public class CPDUI{


    private JPanel panelMain;
    private JButton btnOpenQuestions;
    private JCheckBox CategoryCheck;
    private JCheckBox NumberCheck;
    private JCheckBox DateCheck;
    private JCheckBox ValueCheck;
    private JCheckBox WordCheck;
    private JCheckBox RoundCheck;
    private JTextField CategoryInput;
    private JTextField NumberInput;
    private JTextField ValueInput;
    private JTextField WordInput;
    private JTextField RoundInput;
    private JTextField DateMin;
    private JTextField DateMax;
    private JCheckBox DateDisplay;
    private JCheckBox CategoryDisplay;
    private JCheckBox NumberDisplay;
    private JCheckBox ValueDisplay;
    private JCheckBox QuestionDisplay;
    private JCheckBox RoundDisplay;
    private JButton btnSearch;
    private JRadioButton airDateRadioButton;
    private JRadioButton categoryRadioButton;
    private JRadioButton showNumberRadioButton;
    private JRadioButton valueRadioButton;
    private JRadioButton roundRadioButton;
    private JCheckBox invertOrderCheckBox;


    public CPDUI () {
        btnOpenQuestions.addActionListener(new ActionListener() {
            @Override
            //AÇÃO DO BOTÃO DO ARQUIVO
            public void actionPerformed(ActionEvent e) {
                if (!(JOptionPane.showConfirmDialog(panelMain, "No need to open a file if it was already loaded in the past.\n Move on to open a file?","u sure", JOptionPane.OK_CANCEL_OPTION)==0))
                    return;
                File file = IOUtils.openFile(panelMain);
                if (file != null) {
                    JOptionPane.showMessageDialog(panelMain, "Please press ok to wait while the intern files are generated!\n A confirmation box will pop up at the end of the process");
                    MainFileAdapter.parseFile(file);
                    ReadTest.ReadAll();
                    ReadTest.MakeTrie();
                    JOptionPane.showMessageDialog(panelMain," All files successfully generated");
                }
            }
        });

        // AÇÃO DO BOTÃO DE BUSCA!!!!!!
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParseRequest();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CPDUI");
        frame.setContentPane(new CPDUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void ParseRequest (){
        boolean value = ValueCheck.isSelected();
        int valueIn  = -1;
        boolean date = DateCheck.isSelected();
        int dateMin = -1 , dateMax = -1;
        boolean showNumb = NumberCheck.isSelected();
        int showNumbIn = -1;
        boolean word = WordCheck.isSelected();
        String wordIn = null;
        boolean category = CategoryCheck.isSelected();
        String categoryIn = null;
        boolean round = RoundCheck.isSelected();
        String roundIn = null;
        boolean validInputs = true;
        boolean validFormat = true;


        // lê atributo de valor entrado
        if (value && ValueInput.getText().contentEquals("")){
            validInputs = false;
        }
        else if(value)
            if (ValueInput.getText().matches("^[0-9]*")) {
                valueIn = Integer.parseInt(ValueInput.getText().toLowerCase());
            }
            else
                validFormat = false;


        // lê data entrada
        if (date && (DateMin.getText().contentEquals("")|| DateMax.getText().contentEquals(""))){
            validInputs = false;
        }
        else if (date) {
            if (validDate(DateMax.getText()) && validDate (DateMin.getText())) {
                dateMin = Integer.parseInt(DateMin.getText().replaceAll("/",""));
                dateMax = Integer.parseInt(DateMax.getText().replaceAll("/",""));
            }
            else
                validFormat = false;
        }

        // le show number entrado
        if (showNumb && NumberInput.getText().contentEquals("")){
            validInputs = false;
        }
        else if (showNumb)
            if (NumberInput.getText().matches("^[0-9]*")) {
                showNumbIn = Integer.parseInt(NumberInput.getText());
            }
            else
                validFormat = false;

        //le palavra entrada
        if (word && WordInput.getText().contentEquals("")){
            validInputs = false;
        }
        else if (word)
            wordIn = WordInput.getText().toLowerCase();

        // le categoria entrada
        if (category && CategoryInput.getText().contentEquals("")){
            validInputs = false;
        }
        else if (category)
            categoryIn = CategoryInput.getText().toLowerCase();

        // le round entrado
        if (round && RoundInput.getText().contentEquals("")){
            validInputs = false;
        }
        else if (round)
            roundIn = RoundInput.getText().toLowerCase();

        if (validInputs && validFormat){
            List<Question> questionList = null;
            questionList = Search.Search(categoryIn, dateMin, dateMax, roundIn, showNumbIn, valueIn, wordIn);
            questionList = Sort.sort (invertOrderCheckBox.isSelected() ,categoryRadioButton.isSelected(), airDateRadioButton.isSelected(), roundRadioButton.isSelected(), showNumberRadioButton.isSelected(), valueRadioButton.isSelected(), questionList);
            Table.ShowTable(questionList, ValueDisplay.isSelected(), QuestionDisplay.isSelected(), DateDisplay.isSelected(), RoundDisplay.isSelected(), NumberDisplay.isSelected(), CategoryDisplay.isSelected());

        }
        else
            if (!validInputs)
                JOptionPane.showMessageDialog(panelMain, "Preencha todos os campos selecionados!");
            else
                JOptionPane.showMessageDialog(panelMain, "Formato de estrada inválido!");

    }
    public static boolean validDate (String date){
        if (!date.matches("^[0-9/]*"))
            return false;
        int dateInt = Integer.parseInt(date.replaceAll("/",""));
        if((dateInt%10000)%100 > 31 || (dateInt%10000)%100 < 1 || (dateInt%10000)/100 > 12  || (dateInt%10000)/100 < 1)
            return false;
        return true;
    }
}

