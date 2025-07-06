package jdbc_CRUD_Operations;

import java.sql.*;
import java.util.Scanner;

public class StudentCrudOperations {

    private static final String URL = "jdbc:postgresql://localhost:5432/coaching";
    private static final String USER = "postgres";
    private static final String PASS = "060105";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            while (true) {
                System.out.println("\n=== Coaching Management Menu ===");
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
                    case 1 -> insertStudent(con, sc);
                    case 2 -> viewAllStudents(con);
                    case 3 -> viewStudentById(con, sc);
                    case 4 -> viewStudentByName(con, sc);
                    case 5 -> updateStudentById(con, sc);
                    case 6 -> deleteStudentById(con, sc);
                    case 7 -> deleteStudentByName(con, sc);
                    case 8 -> viewStudentsWithHighFees(con);
                    case 9 -> {
                        con.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INSERT
    public static void insertStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Fees: ");
        int fees = sc.nextInt();

        String sql = "INSERT INTO student (id, name, age, fees) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.setInt(4, fees);
        ps.executeUpdate();
        System.out.println("Student inserted successfully.");
    }

    // VIEW ALL
    public static void viewAllStudents(Connection con) throws SQLException {
        String sql = "SELECT * FROM student";
        ResultSet rs = con.createStatement().executeQuery(sql);
        System.out.println("\nAll Students:");
        while (rs.next()) {
            printStudent(rs);
        }
    }

    // VIEW BY ID
    public static void viewStudentById(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        String sql = "SELECT * FROM student WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) printStudent(rs);
        else System.out.println("No student found with ID " + id);
    }

    // VIEW BY NAME
    public static void viewStudentByName(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        String sql = "SELECT * FROM student WHERE name = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        boolean found = false;
        while (rs.next()) {
            printStudent(rs);
            found = true;
        }
        if (!found) System.out.println("No student found with name " + name);
    }

    // UPDATE BY ID
    public static void updateStudentById(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter New Age: ");
        int age = sc.nextInt();
        System.out.print("Enter New Fees: ");
        int fees = sc.nextInt();

        String sql = "UPDATE student SET age = ?, fees = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, age);
        ps.setInt(2, fees);
        ps.setInt(3, id);
        int updated = ps.executeUpdate();
        System.out.println(updated > 0 ? "Updated successfully." : "No record found.");
    }

    // DELETE BY ID
    public static void deleteStudentById(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM student WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int deleted = ps.executeUpdate();
        System.out.println(deleted > 0 ? "Deleted successfully." : "No record found.");
    }

    // DELETE BY NAME
    public static void deleteStudentByName(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        String sql = "DELETE FROM student WHERE name = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        int deleted = ps.executeUpdate();
        System.out.println(deleted > 0 ? "Deleted successfully." : "No record found.");
    }

    // VIEW FEES ≥ 500
    public static void viewStudentsWithHighFees(Connection con) throws SQLException {
        String sql = "SELECT * FROM student WHERE fees >= 500";
        ResultSet rs = con.createStatement().executeQuery(sql);
        System.out.println("\nStudents with Fees ≥ 500:");
        while (rs.next()) {
            printStudent(rs);
        }
    }

    public static void printStudent(ResultSet rs) throws SQLException {
        System.out.println("ID: " + rs.getInt("id"));
        System.out.println("Name: " + rs.getString("name"));
        System.out.println("Age: " + rs.getInt("age"));
        System.out.println("Fees: " + rs.getInt("fees"));
        System.out.println("--------------------");
    }
}
