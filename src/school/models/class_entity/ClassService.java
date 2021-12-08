package school.models.class_entity;

import school.helper.DBConnection;
import school.models.section.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassService {

    public boolean insertClass(ClassEntity c) {
     boolean condition = false;

     String sql = "INSERT INTO classes (name, section_id) VALUES (?,?)";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setInt(2, c.getSection_id());

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

    public List<ClassSection> getAllClass() {
        List<ClassSection> classSections = new ArrayList<>();
        String sql = "SELECT c.id as id,c.name as name, c.section_id as section_id, sections.section as section " +
                "FROM classes as c " +
                "LEFT JOIN sections " +
                "ON c.section_id = sections.id";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Section section = new Section(resultSet.getInt("section_id"), resultSet.getString("section"));
                classSections.add(new ClassSection(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        section
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,resultSet);
        return classSections;
    }

    public List<ClassEntity> getAllClassName() {
        List<ClassEntity> classEntities = new ArrayList<>();
        String sql = "SELECT DISTINCT name,id FROM classes GROUP BY name";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                classEntities.add(
                        new ClassEntity(
                                resultSet.getInt("id"),
                                0,
                                resultSet.getString("name")
                                )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
                DBConnection.closeConnection(connection,ps,resultSet);
        return classEntities;
    }

    public List<ClassSection> getAllClassBySearch(String txt) {
        List<ClassSection> classSections = new ArrayList<>();
        String sql = "SELECT c.id as id,c.name as name, c.section_id as section_id, sections.section as section " +
                "FROM classes as c " +
                "LEFT JOIN sections " +
                "ON c.section_id = sections.id " +
                "WHERE c.name LIKE '%"+ txt + "%' ";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Section section = new Section(resultSet.getInt("section_id"), resultSet.getString("section"));
                classSections.add(new ClassSection(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        section
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,resultSet);
        return classSections;
    }

    public boolean deleteClassById(int id) {
        boolean condition = false;
        String sql = "DELETE FROM classes  WHERE id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
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

    public boolean updateClass(ClassEntity classEntity) {
        boolean condition = false;
        String sql = "UPDATE classes SET name=?, section_id=? WHERE id=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, classEntity.getName());
            ps.setInt(2, classEntity.getSection_id());
            ps.setInt(3, classEntity.getId());
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
