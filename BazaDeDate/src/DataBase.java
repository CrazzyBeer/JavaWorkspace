import java.io.BufferedReader;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class DataBase {
      private Vector<Doctor> Doctors;
      private BufferedReader readDocs;
      private BufferedReader readPacs;
        //CONSTRUCTOR
        public DataBase() {
            int number;
            Doctors = new Vector<Doctor>();
            try {
                File docs = new File("Doctors.txt");
                if (docs.createNewFile()) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(docs));
                    writer.write("0");
                    writer.close();
                }
                
                File pacs = new File("Pacients.txt");
                if (pacs.createNewFile()) {
                }
                
                readDocs = new BufferedReader(new FileReader(docs));
                readPacs = new BufferedReader(new FileReader(pacs));
                number = Integer.parseInt(readDocs.readLine());
                for (int i=1; i<=number; i++) {

                    Doctor d = new Doctor(readDocs.readLine(),
                            Integer.parseInt(readDocs.readLine()),
                            Integer.parseInt(readDocs.readLine()),
                            Integer.parseInt(readDocs.readLine()));
                  
                        for (int j=1;j<=d.getFilePacients(); j++) {
                            Pacient p = new Pacient(
                                   readPacs.readLine(),
                                   readPacs.readLine(),
                                   Integer.parseInt(readPacs.readLine()));
                           d.addPacient(p);
                        }
                    Doctors.addElement(d);
                }
               readDocs.close(); 
               readPacs.close();
               
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eroare citire fisiere");
                e.printStackTrace();
            }
            
        }
        
        public Vector<String> getDoctorNames() {
            Vector <String> names = new Vector<String>();
            for (int i=0;i<Doctors.size();i++) {
                Doctor d = Doctors.get(i);
                names.add(d.getName());
            }
            return names;
        }
        
        public Vector<String> getPacientNames(int i) {
            return Doctors.get(i).getPacientNames();
        }
        
        public Vector <Doctor> getDoctorVector() {
            return Doctors;
        }
        
        public Doctor getDoctor(int i) {
            return Doctors.get(i);
        }
        
        public void deletePacient(int i,int j) {
            Doctor d = Doctors.get(j);
            if (d.getPacientVector().size()!=0) {
                d.deletePacient(i);
                Doctors.remove(j);
                Doctors.add(j,d);
            }

        }
        
        public void deleteDoctor(int i) {
            if (Doctors.size()!=0) Doctors.remove(i); 
        }
        
        public void addDoctor(Doctor d)  {
            Doctors.addElement(d);
        }
        
        public void addPacient(Pacient p,int i)  {
            Doctor d = Doctors.get(i);
            d.addPacient(p);
            Doctors.remove(i);
            Doctors.add(i,d);
        }
        
        public void reWrite() throws IOException {
            try {
                File docs = new File("Doctors.txt");
                File pacs = new File("Pacients.txt");
                PrintWriter writeDocs = new PrintWriter(docs);
                PrintWriter writePacs  = new PrintWriter(pacs);
                writeDocs.println(Doctors.size());
                for (int i=0; i<Doctors.size();i++) {
                    Doctor d = Doctors.get(i);
                    writeDocs.println(d.getName());
                    writeDocs.println(d.getAge());
                    writeDocs.println(d.getSalary());
                    writeDocs.println(d.getPacientVector().size());
                    
                    for (int j=0;j<d.getPacientVector().size();j++) {
                        Pacient p = d.getPacientVector().get(j);
                        writePacs.println(p.getName());
                        writePacs.println(p.getDisease());
                        writePacs.println(p.getAge());

                    }
                }
                
                writeDocs.close();
                writePacs.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
}
