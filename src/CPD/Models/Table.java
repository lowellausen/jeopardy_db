package CPD.Models;

import CPD.Models.Question;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.List;
/**
 * Created by Leonardo on 18/06/2016.
 */
public class Table extends JFrame {

    JTable table;

    public Table (List<Question> list ,boolean valueDisplay,boolean questionDisplay,boolean dateDisplay,boolean roundDisplay,boolean numberDisplay,boolean categoryDisplay){
        setLayout(new FlowLayout());

        String columnNames[] = {"Question","Answer","Value","Category","Round", "AirDate", "#Show", ""};
        Object data [][] = new Object[list.size()][8];

        for (int i = 0; i <list.size() ; i++) {
            data[i][0] = list.get(i).question;
            data[i][1] = list.get(i).answer;
            data[i][2] = list.get(i).value;
            data[i][3] = list.get(i).category;
            data[i][4] = list.get(i).round;
            String date = retrieveDate (list.get(i).airDate);
            data[i][5] = date;
            data[i][6] = list.get(i).showNumber;
            data[i][7] = "ANSWER";
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable( model );

        table.setPreferredScrollableViewportSize(new Dimension(1300,400));
        table.setFillsViewportHeight(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(650);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setMinWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(50);
        table.getColumnModel().getColumn(3).setMinWidth(200);
        table.getColumnModel().getColumn(4).setWidth(100);
        table.getColumnModel().getColumn(5).setMinWidth(75);
        table.getColumnModel().getColumn(5).setMaxWidth(75);
        table.getColumnModel().getColumn(6).setMinWidth(50);
        table.getColumnModel().getColumn(6).setMaxWidth(50);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(1));

        int removeds = 0;
        if(questionDisplay){
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0-removeds++));
        }
        if(valueDisplay){
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(1-removeds++));
        }
        if(categoryDisplay){
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2-removeds++));
        }

        if(roundDisplay){
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3-removeds++));
        }
        if(dateDisplay){
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(4-removeds++));
        }
        if(numberDisplay){
            table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5-removeds++));
        }

        JScrollPane scrollPane = new JScrollPane(table);
        Action Answer = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                JOptionPane.showMessageDialog(scrollPane,table.getModel().getValueAt(modelRow, 1));

            }

        };

        ButtonColumn buttonColumn = new ButtonColumn(table, Answer, 6-removeds);
        buttonColumn.setMnemonic(KeyEvent.VK_D);


        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);


        sorter.setComparator( 2 , new Comparator<Integer>() {

            @Override
            public int compare(Integer name1, Integer name2) {
                return name1-name2;
            }
        });
        sorter.setComparator( 6 , new Comparator<Integer>() {

            @Override
            public int compare(Integer name1, Integer name2) {
                return name1-name2;
            }
        });

        add(scrollPane);

    }

    public static void ShowTable (List<Question> list,boolean valueDisplay,boolean questionDisplay,boolean dateDisplay,boolean roundDisplay,boolean numberDisplay,boolean categoryDisplay){
        Table gui = new Table(list, valueDisplay, questionDisplay,dateDisplay,roundDisplay,numberDisplay,categoryDisplay);
        gui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        gui.setSize(1400,500);
        gui.setVisible(true);
        gui.setTitle("Table");
    }

    public static String retrieveDate (int dateInt){
        String date = "";
        int aux = 0;

        aux = dateInt%10000;
        dateInt = dateInt / 10000;
        date = date.concat(Integer.toString(dateInt)).concat("/");
        dateInt = aux%100;
        aux = aux /100;
        date = date.concat(Integer.toString(aux)).concat("/").concat(Integer.toString(dateInt));

        return date;
    }
}
