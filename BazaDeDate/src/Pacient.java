
public class Pacient {
    private String name;
    private int age;
    private String disease;
    public Pacient(String name,String disease,int age) { 
        this.name=name;
        this.disease=disease;
        this.age=age;
    }
    
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    
    public String getDisease() {
        return disease;
    }
}
