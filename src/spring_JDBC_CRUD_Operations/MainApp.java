package spring_JDBC_CRUD_Operations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_JDBC_CRUD_Operations.config.DBConfig;
import spring_JDBC_CRUD_Operations.dao.StudentDAO;
import spring_JDBC_CRUD_Operations.model.Student;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
        StudentDAO dao = context.getBean(StudentDAO.class);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Coaching Management System Menu =====");
            System.out.println("1. Insert Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student by ID");
            System.out.println("4. View Student by Name");
            System.out.println("5. Update Student Age & Fees by ID");
            System.out.println("6. Delete Student by ID");
            System.out.println("7. Delete Student by Name");
            System.out.println("8. Show Students Paid Fees ≥ 500");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    System.out.print("Enter Fees: ");
                    int fees = sc.nextInt();
                    try {
	                    Student s = new Student();
	                    s.setId(id);
	                    s.setName(name);
	                    s.setAge(age);
	                    s.setFees(fees);
	                    dao.insert(s);
                    }catch(Exception e) {
                    	System.out.println("Already this id is present.");
                    }
                }

                case 2 -> {
                    List<Student> all = dao.getAll();
                    for (Student st : all) {
                        printStudent(st);
                    }
                }

                case 3 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    try {
                        Student st = dao.getById(id);
                        printStudent(st);
                    } catch (Exception e) {
                        System.out.println("Student not found.");
                    }
                }

                case 4 -> {
                    System.out.print("Enter Name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    List<Student> list = dao.getByName(name);
                    if (list.isEmpty()) {
                        System.out.println("No student found.");
                    } else {
                        list.forEach(MainApp::printStudent);
                    }
                }

                case 5 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter New Age: ");
                    int newAge = sc.nextInt();
                    System.out.print("Enter New Fees: ");
                    int newFees = sc.nextInt();
                    dao.updateById(id, newAge, newFees);
                }

                case 6 -> {
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    dao.deleteById(id);
                }

                case 7 -> {
                    System.out.print("Enter Name to delete: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    List<Student> list = dao.getByName(name);
                    if (list.isEmpty()) {
                        System.out.println("No student found.");
                    } else {
                        for (Student st : list) {
                            dao.deleteById(st.getId());
                        }
                    }
                }

                case 8 -> {
                    List<Student> list = dao.getFeesAbove500();
                    if (list.isEmpty()) {
                        System.out.println("No student has paid ≥ 500 fees.");
                    } else {
                        list.forEach(MainApp::printStudent);
                    }
                }

                case 9 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void printStudent(Student st) {
        System.out.println("\nID: " + st.getId());
        System.out.println("Name: " + st.getName());
        System.out.println("Age: " + st.getAge());
        System.out.println("Fees: " + st.getFees());
    }
}
