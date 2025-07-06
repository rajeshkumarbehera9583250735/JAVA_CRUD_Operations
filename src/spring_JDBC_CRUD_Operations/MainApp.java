package spring_JDBC_CRUD_Operations;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring_JDBC_CRUD_Operations.config.DBConfig;
import spring_JDBC_CRUD_Operations.dao.StudentDAO;
import spring_JDBC_CRUD_Operations.model.Student;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DBConfig.class);
        StudentDAO dao = context.getBean(StudentDAO.class);

        // Insert student
        Student s = new Student();
        s.setId(1);
        s.setName("Rajesh");
        s.setAge(22);
        s.setFees(500);
        dao.insert(s);

        // Get all students
        List<Student> all = dao.getAll();
        for (Student st : all) {
            System.out.println(st.getId() + " | " + st.getName() + " | " + st.getAge() + " | " + st.getFees());
        }
    }
}
