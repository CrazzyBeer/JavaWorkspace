import java.util.Vector;



public class Doctor {
    private String name;
    private int age;
    private int salary;
    private Vector <Pacient> Pacients;
    private int pacients;
    
    public Doctor(String name,int age,int salary,int pacients) {
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.pacients = pacients;
        Pacients = new Vector <Pacient>();
    }
    public Doctor() {        
    }
    
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    
    public int getSalary() {
        return salary;
    }
    public Vector<Pacient> getPacientVector() {
        return Pacients;
    }
    public int getFilePacients() {
        return pacients;
    }
    
    public Vector<String> getPacientNames() {
        Vector <String> names = new Vector<String>();
        for (int i=0;i<Pacients.size();i++) 
            names.add(Pacients.get(i).getName());
        return names;
    }
    
    public Pacient getPacient(int i) {
        return Pacients.get(i);
    }
    
    
    public void deletePacient(int i) {
        Pacients.remove(i);
    }
    public void addPacient(Pacient p) {
        Pacients.add(p);
        
    }

}
