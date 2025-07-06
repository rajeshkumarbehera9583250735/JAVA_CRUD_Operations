package spring_JDBC_CRUD_Operations.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spring_JDBC_CRUD_Operations.model.Student;

import java.util.List;

@Repository
public class StudentDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public void insert(Student s) {
        String sql = "INSERT INTO student (id, name, age, fees) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, s.getId(), s.getName(), s.getAge(), s.getFees());
        System.out.println("Inserted student.");
    }

    public void updateById(int id, int newAge, int newFees) {
        String sql = "UPDATE student SET age = ?, fees = ? WHERE id = ?";
        jdbc.update(sql, newAge, newFees, id);
        System.out.println("Updated student.");
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        jdbc.update(sql, id);
        System.out.println("Deleted student.");
    }

    public List<Student> getAll() {
        return jdbc.query("SELECT * FROM student", new StudentRowMapper());
    }

    public Student getById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        return jdbc.queryForObject(sql, new StudentRowMapper(), id);
    }

    public List<Student> getByName(String name) {
        String sql = "SELECT * FROM student WHERE name = ?";
        return jdbc.query(sql, new StudentRowMapper(), name);
    }

    public List<Student> getFeesAbove500() {
        String sql = "SELECT * FROM student WHERE fees >= 500";
        return jdbc.query(sql, new StudentRowMapper());
    }
}
