package spring_JDBC_CRUD_Operations.model;

public class Student {
    private int id;
    private String name;
    private int age;
    private int fees;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public int getFees() {
        return fees;
    }
    public void setFees(int fees) {
        this.fees = fees;
    }
}
