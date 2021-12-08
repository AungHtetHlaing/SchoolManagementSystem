package school.models.teacher;

import school.helper.DBConnection;
import school.models.class_entity.ClassEntity;
import school.models.section.Section;
import school.models.student.Student;
import school.models.subject.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    public boolean insertTeacher(Teacher teacher) {
        boolean condition = false;
        String sql = "INSERT INTO teachers (name,age,phone,address,gender,subject_id,class_id,section_id) VALUES (?,?,?,?,?,?,?,?)";
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,teacher.getName());
            ps.setInt(2,teacher.getAge());
            ps.setString(3,teacher.getPhone());
            ps.setString(4,teacher.getAddress());
            ps.setString(5,teacher.getGender());
            ps.setInt(6,teacher.getSubject().getId());
            ps.setInt(7,teacher.getClassEntity().getId());
            ps.setInt(8,teacher.getSection().getId());
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

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.id, t.name, t.age, t.phone, t.address, t.gender,t.created_at ,c.id AS class_id ,c.name AS class_name, s.id AS section_id ,s.section, sub.id AS subject_id, sub.name AS subject_name FROM teachers AS t JOIN classes AS c JOIN sections AS s JOIN subjects AS sub ON t.class_id=c.id AND t.section_id=s.id AND t.subject_id=sub.id";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                teachers.add(
                        new Teacher(
                                resultSet.getInt("id"),
                                resultSet.getInt("age"),
                                resultSet.getString("phone"),
                                resultSet.getString("gender"),
                                new ClassEntity(resultSet.getInt("class_id"),0, resultSet.getString("class_name")),
                                new Section(resultSet.getInt("section_id"), resultSet.getString("section")),
                                new Subject(resultSet.getInt("subject_id"),resultSet.getString("subject_name")),
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
        return teachers;
    }

    public List<Teacher> getAllTeachersBySearch(String searchTxt) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT t.id, t.name, t.age, t.phone, t.address, t.gender,t.created_at ,c.id AS class_id ,c.name AS class_name, s.id AS section_id ,s.section, sub.id AS subject_id, sub.name AS subject_name FROM teachers AS t JOIN classes AS c JOIN sections AS s JOIN subjects AS sub ON t.class_id=c.id AND t.section_id=s.id AND t.subject_id=sub.id WHERE t.name LIKE '%"+searchTxt+"%'";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                teachers.add(
                        new Teacher(
                                resultSet.getInt("id"),
                                resultSet.getInt("age"),
                                resultSet.getString("phone"),
                                resultSet.getString("gender"),
                                new ClassEntity(resultSet.getInt("class_id"),0, resultSet.getString("class_name")),
                                new Section(resultSet.getInt("section_id"), resultSet.getString("section")),
                                new Subject(resultSet.getInt("subject_id"),resultSet.getString("subject_name")),
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
        return teachers;

    }

    public boolean deleteTeacherById(int id) {
        boolean condition = false;
        String sql = "DELETE FROM teachers WHERE id=?";
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

    public boolean updateTeacher(Teacher teacher) {
        boolean condition = false;
        String sql = "UPDATE teachers SET name=?, age=?, phone=?, address=?, gender=?, class_id=?, section_id=?, subject_id=? WHERE id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, teacher.getName());
            ps.setInt(2, teacher.getAge());
            ps.setString(3, teacher.getPhone());
            ps.setString(4, teacher.getAddress());
            ps.setString(5, teacher.getGender());
            ps.setInt(6, teacher.getClassEntity().getId());
            ps.setInt(7, teacher.getSection().getId());
            ps.setInt(8, teacher.getSubject().getId());
            ps.setInt(9, teacher.getId());
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
