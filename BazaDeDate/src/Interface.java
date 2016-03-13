import java.awt.EventQueue;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Interface extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface frame = new Interface();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @throws IOException 
     */
    public Interface() throws IOException {
        initGUI();
    }
    private void initGUI() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 576, 498);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(false);
        
        //DB DECLARE
        final DataBase db = new DataBase();
        
        final JList <String>list = new JList<String>(db.getDoctorNames());
        list.setBounds(10, 89, 202, 362);
        contentPane.add(list);
        
        final JList<String> list_1 = new JList<String>();
        list_1.setBounds(222, 89, 202, 362);
        contentPane.add(list_1);
        
        JButton btnDoctorBio = new JButton("Doctor BIO");
        btnDoctorBio.setBounds(434, 86, 106, 54);
        contentPane.add(btnDoctorBio);
        btnDoctorBio.setFocusable(false);
        
        JButton btnPacientBIO = new JButton("Pacient BIO");
        btnPacientBIO.setBounds(434, 151, 106, 54);
        contentPane.add(btnPacientBIO);
        btnPacientBIO.setFocusable(false);
        
        JButton btnAddDoctor = new JButton("Add Doctor");
        btnAddDoctor.setBounds(10, 11, 120, 57);
        contentPane.add(btnAddDoctor);
        btnAddDoctor.setFocusable(false);
        
        JButton btnAddPacient = new JButton("Add Pacient");
        btnAddPacient.setBounds(140, 11, 115, 57);
        contentPane.add(btnAddPacient);
        btnAddPacient.setFocusable(false);
        
        JButton btnDeleteDoctor = new JButton("Delete Doctor");
        btnDeleteDoctor.setBounds(265, 12, 115, 54);
        contentPane.add(btnDeleteDoctor);
        btnDeleteDoctor.setFocusable(false);
        
        JButton btnDeletePacient = new JButton("Delete Pacient");
        btnDeletePacient.setBounds(390, 12, 150, 54);
        contentPane.add(btnDeletePacient);
        btnDeletePacient.setFocusable(false);
        
        //LISTENERS
       list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                int index = list.getSelectedIndex();
                if (index!=-1) 
                    list_1.setListData(db.getPacientNames(list.getSelectedIndex()));
                    else list_1.setListData(new Vector<String>());
                              
            }
            
        });
       btnDoctorBio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (list.getSelectedIndex()!=-1) {
                        Doctor d = db.getDoctor(list.getSelectedIndex());
                        JOptionPane.showMessageDialog(null, "Nume: "+d.getName()+"\n" 
                                + "Virsta: " +d.getAge() + "\n" + "Salariul: "+d.getSalary()+"\n"+
                                "Numarul Pacientilor: "+d.getPacientVector().size());
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Selectati un doctor");
                }
            }
        });
       
       btnPacientBIO.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
               try {
                   if (list.getSelectedIndex()!=-1 && list_1.getSelectedIndex()!=-1) {
                       Pacient p = db.getDoctor(list.getSelectedIndex()).getPacient(list_1.getSelectedIndex());
                       JOptionPane.showMessageDialog(null, "Nume: "+p.getName()+"\n" 
                               + "Virsta: " +p.getAge() + "\n" + "Boala: "+p.getDisease()+"\n");
                   }
               }
               catch (Exception e) {
                   JOptionPane.showMessageDialog(null, "Selectati un pacient");
               }
           }
       });
       
       btnDeletePacient.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            try {
                 if (list.getSelectedIndex()!=-1 && list_1.getSelectedIndex()!=-1)
                 {
                      db.deletePacient(list_1.getSelectedIndex(),list.getSelectedIndex());
                      list_1.setListData(db.getDoctor(list.getSelectedIndex()).getPacientNames());
                      list_1.clearSelection();
                      db.reWrite();
                 } else if (list.getSelectedIndex()==-1) JOptionPane.showMessageDialog(null, "Selectati un doctor");
                 else if (list_1.getSelectedIndex()==-1) JOptionPane.showMessageDialog(null, "Selectati un pacient");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
           
       });
       
       btnDeleteDoctor.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
               try {
                   if (list.getSelectedIndex()!=-1)
                   {
                       db.deleteDoctor(list.getSelectedIndex());
                       list.setListData(db.getDoctorNames());
                       list.clearSelection();
                       db.reWrite();
                   }
               }
               catch (Exception e) {
                   JOptionPane.showMessageDialog(null, "Nu este posibila stergerea");
               }
           }
       });
       
       btnAddDoctor.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
               
               JTextField name = new JTextField("Doctor "+db.getDoctorVector().size());
               JTextField age = new JTextField("20");
               JTextField salary = new  JTextField("2000");
               Object[] fields = {
                       "Nume: ",name,
                       "Virsta: ",age,
                       "Salariul ",salary
               };    
               int option = JOptionPane.showConfirmDialog(null,fields,"Adauga un doctor in lista",JOptionPane.YES_NO_OPTION); 
               if (option == 0) {
                       try {
                           Doctor d = new Doctor(name.getText(),Integer.parseInt(age.getText()),Integer.parseInt(salary.getText()),0);
                           db.addDoctor(d);
                           list.setListData(db.getDoctorNames());
                           db.reWrite();
                       }
                       catch (Exception e) {
                           JOptionPane.showMessageDialog(null,"Eroare");
                       }
               }
           }
       });
       
       btnAddPacient.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
               if (list.getSelectedIndex()!=-1) {
                   JTextField name = new JTextField("Pacient "+db.getDoctor(list.getSelectedIndex()).getPacientVector().size());
                   JTextField age = new JTextField("0");
                   JTextField disease = new  JTextField("Boala");
                   Object[] fields = {
                           "Nume: ",name,
                           "Diagnoza  ",disease,
                           "Virsta: ",age
                   };    
                   int option = JOptionPane.showConfirmDialog(null,fields,"Adauga un pacient in lista",JOptionPane.YES_NO_OPTION); 
                   if (option == 0) {
                           try {
                               Pacient p = new Pacient(name.getText(),disease.getText(),Integer.parseInt(age.getText()));
                               db.addPacient(p, list.getSelectedIndex());
                               list_1.setListData(db.getPacientNames(list.getSelectedIndex()));
                               db.reWrite();
                           }
                           catch (Exception e) {
                               JOptionPane.showMessageDialog(null,"Eroare");
                           }
                   }
               } else JOptionPane.showMessageDialog(null, "Selectati un doctor");
           }
       });
       
    }
}
