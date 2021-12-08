package school.models.subject;

import school.helper.DBConnection;
import school.models.class_entity.ClassEntity;
import school.models.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectService {

    public boolean insertSubject(Subject subject) {
        boolean condition = false;
        Subject subjects = getSubjectByName(subject.getName());
        Connection connection = null;
        PreparedStatement ps = null;

        if (subjects == null) {
            String sql = "INSERT INTO subjects (name) VALUES (?)";
             connection = DBConnection.getConnection();
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, subject.getName());
                int result = ps.executeUpdate();
                if (result == 1) {
                    condition = true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        DBConnection.closeConnection(connection, ps, null);
        return condition;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                subjects.add(
                        new Subject(resultSet.getInt("id"), resultSet.getString("name"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
                DBConnection.closeConnection(connection, ps, resultSet);
        return subjects;
    }

    public Subject getSubjectByName(String name) {
        Subject subject = null;
        String sql = "SELECT * FROM subjects WHERE name=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                subject = new Subject(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
                DBConnection.closeConnection(connection, ps, resultSet);
        return subject;
    }


    public boolean insertSubjectIdAndClassId(int class_id, int subject_id) {
        boolean condition = false;
        Connection connection = null;
        PreparedStatement ps = null;
        if (!getSubjectIdAndClassId(class_id, subject_id)){
            String sql = "INSERT INTO class_subject VALUES (?,?)";
            connection = DBConnection.getConnection();
            try {
                ps = connection.prepareStatement(sql);
                ps.setInt(1, class_id);
                ps.setInt(2, subject_id);
                int result = ps.executeUpdate();
                if (result == 1) {
                    condition = true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        DBConnection.closeConnection(connection, ps, null);

        return condition;
    }

    public boolean getSubjectIdAndClassId(int class_id, int subject_id) {
        boolean condition = false;
        String sql = "SELECT * FROM class_subject WHERE class_id=? AND subject_id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, class_id);
            ps.setInt(2, subject_id);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
             condition = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection, ps, resultSet);

        return condition;
    }

    public List<SubjectClass> getSubjectNameAndClassName() {
        List<SubjectClass> subjectClasses = new ArrayList<>();

        String sql = "SELECT c.id AS class_id, c.name AS class_name, s.id AS subject_id, s.name AS subject_name FROM classes AS c JOIN class_subject AS cs JOIN subjects AS s ON c.id=cs.class_id AND s.id=cs.subject_id";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ClassEntity classEntity = new ClassEntity(resultSet.getInt("class_id"),0,resultSet.getString("class_name"));
                Subject subject = new Subject(resultSet.getInt("subject_id"),  resultSet.getString("subject_name"));
                subjectClasses.add(
                        new SubjectClass(
                                classEntity,
                                subject
                        )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        DBConnection.closeConnection(connection,ps,resultSet);

        return subjectClasses;
    }

    public List<SubjectClass> getSubjectNameAndClassNameBySearch(String className) {
        List<SubjectClass> subjectClasses = new ArrayList<>();

        String sql = "SELECT c.id AS class_id, c.name AS class_name, s.id AS subject_id, s.name AS subject_name FROM classes AS c JOIN class_subject AS cs JOIN subjects AS s ON c.id=cs.class_id AND s.id=cs.subject_id WHERE c.name LIKE '%"+ className + "%' ";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ClassEntity classEntity = new ClassEntity(resultSet.getInt("class_id"),0,resultSet.getString("class_name"));
                Subject subject = new Subject(resultSet.getInt("subject_id"),  resultSet.getString("subject_name"));
                subjectClasses.add(
                        new SubjectClass(
                                classEntity,
                                subject
                               )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        DBConnection.closeConnection(connection,ps,resultSet);

        return subjectClasses;
    }

    public boolean deleteSubjectAndClassId(int class_id, int subject_id) {
        boolean condition = false;
        String sql = "DELETE FROM class_subject WHERE class_id=? AND subject_id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, class_id);
            ps.setInt(2, subject_id);
            int result = ps.executeUpdate();
            if (result == 1) {
                condition = true;
                deleteSubject();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
                DBConnection.closeConnection(connection,ps,null);

        return condition;
    }

    public boolean updateSubjectIdAndClassId(int class_id, int subject_id, int old_class_id) {
        boolean condition = false;
        String sql = "UPDATE class_subject SET class_id=?, subject_id=? WHERE class_id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, class_id);
            ps.setInt(2, subject_id);
            ps.setInt(3, old_class_id);
            int result = ps.executeUpdate();
            if (result == 1) {
                condition = true;
                deleteSubject();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        DBConnection.closeConnection(connection,ps,null);

        return condition;
    }

    public void deleteSubject() {

        String sql = "DELETE FROM subjects WHERE id NOT IN (SELECT cs.subject_id FROM class_subject AS cs)";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
                DBConnection.closeConnection(connection,ps,null);
    }
}
