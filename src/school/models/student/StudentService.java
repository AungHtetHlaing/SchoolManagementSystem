package school.models.student;

import school.helper.DBConnection;
import school.models.class_entity.ClassEntity;
import school.models.section.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    public boolean insertStudent(Student student) {
        boolean condition = false;
        String sql = "INSERT INTO students (name,age,phone,address,gender,class_id,section_id) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        Connection connection = DBConnection.getConnection();

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2,student.getAge());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getGender());
            ps.setInt(6, student.getClassEntity().getId());
            ps.setInt(7, student.getSection().getId());
            int result =  ps.executeUpdate();
            if (result == 1) {
                condition = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,null);
        return condition;

    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT stu.id, stu.name, stu.age, stu.phone, stu.address, stu.gender,stu.created_at ,c.id AS class_id ,c.name AS class_name, s.id AS section_id ,s.section FROM students AS stu JOIN classes AS c JOIN sections AS s ON stu.class_id=c.id AND stu.section_id=s.id";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                students.add(
                        new Student(
                                resultSet.getInt("id"),
                                resultSet.getInt("age"),
                                resultSet.getString("phone"),
                                resultSet.getString("gender"),
                                new ClassEntity(resultSet.getInt("class_id"),0, resultSet.getString("class_name")),
                                new Section(resultSet.getInt("section_id"), resultSet.getString("section")),
                                resultSet.getString("name"),
                                resultSet.getString("address"),
                                resultSet.getString("created_at")
                                )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
DBConnection.closeConnection(connection,ps,resultSet);
        return students;

    }

    public List<Student> getAllStudentsBySearch(String searchTxt) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT stu.id, stu.name, stu.age, stu.phone, stu.address, stu.gender,stu.created_at ,c.id AS class_id ,c.name AS class_name, s.id AS section_id ,s.section FROM students AS stu JOIN classes AS c JOIN sections AS s ON stu.class_id=c.id AND stu.section_id=s.id WHERE stu.name LIKE '%"+searchTxt+"%'";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                students.add(
                        new Student(
                                resultSet.getInt("id"),
                                resultSet.getInt("age"),
                                resultSet.getString("phone"),
                                resultSet.getString("gender"),
                                new ClassEntity(resultSet.getInt("class_id"),0, resultSet.getString("class_name")),
                                new Section(resultSet.getInt("section_id"), resultSet.getString("section")),
                                resultSet.getString("name"),
                                resultSet.getString("address"),
                                resultSet.getString("created_at")
                        )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
DBConnection.closeConnection(connection,ps,resultSet);
        return students;

    }

    public boolean deleteStudentById(int id) {
        boolean condition = false;
        String sql = "DELETE FROM students WHERE id=?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result == 1){
                condition  = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,null);
        return condition;
    }

    public boolean updateStudent(Student student) {
        boolean condition = false;
        String sql = "UPDATE students SET name=?, age=?, phone=?, address=?, gender=?, class_id=?, section_id=? WHERE id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getGender());
            ps.setInt(6, student.getClassEntity().getId());
            ps.setInt(7, student.getSection().getId());
            ps.setInt(8, student.getId());
            int result = ps.executeUpdate();
            if (result == 1) {
                condition = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

                DBConnection.closeConnection(connection,ps,null);
        return condition;
    }
}
