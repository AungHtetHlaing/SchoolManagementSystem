package school.models.pay_amount;

import school.helper.DBConnection;
import school.models.class_entity.ClassEntity;
import school.models.section.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayAmountService {
    public boolean insertPayAmount(PayAmount payAmount) {
        boolean condition = false;

        String sql = "INSERT INTO pay_amount (class_id, section_id, teacher_amount, student_amount) VALUES (?,?,?,?)";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, payAmount.getClassEntity().getId());
            ps.setInt(2, payAmount.getSection().getId());
            ps.setInt(3, payAmount.getTeacher_amount());
            ps.setInt(4,payAmount.getStudent_amount());
            int result = ps.executeUpdate();
            if (result == 1) {
                condition = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        DBConnection.closeConnection(connection, ps, null);

        return condition;
    }

    public List<PayAmount> getAllPayAmount() {
        List<PayAmount> payAmounts = new ArrayList<>();

        String sql = "SELECT pa.id,pa.class_id,c.name AS class_name, pa.section_id ,s.section AS section_name, pa.teacher_amount, pa.student_amount FROM pay_amount AS pa JOIN classes AS c JOIN sections AS s ON c.id=pa.class_id AND s.id=pa.section_id";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ClassEntity classEntity = new ClassEntity(resultSet.getInt("class_id"),0, resultSet.getString("class_name"));
                Section section = new Section(resultSet.getInt("section_id"),resultSet.getString("section_name"));
                payAmounts.add(
                        new PayAmount(
                                resultSet.getInt("id"),
                                classEntity,
                                section,
                                resultSet.getInt("teacher_amount"),
                                resultSet.getInt("student_amount")
                        )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,resultSet);
        return payAmounts;
    }

    public boolean deletePayAmountById(int id) {
        boolean condition = false;
        String sql = "DELETE FROM pay_amount WHERE id=?";
        Connection connection =DBConnection.getConnection();
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
        DBConnection.closeConnection(connection,ps, null);
        return condition;
    }

    public boolean updatePayAmount(PayAmount payAmount, int payAmount_id) {
        boolean condition = false;
        String sql = "UPDATE pay_amount SET class_id=?, section_id=?, teacher_amount=?, student_amount=?  WHERE id=?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,payAmount.getClassEntity().getId());
            ps.setInt(2, payAmount.getSection().getId());
            ps.setInt(3,payAmount.getTeacher_amount());
            ps.setInt(4,payAmount.getStudent_amount());
            ps.setInt(5,payAmount_id);
            int result = ps.executeUpdate();
            if (result == 1) {
                condition = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
                DBConnection.closeConnection(connection,ps, null);

        return condition;
    }

    public List<PayAmount> getAllPayAmountBySearch(String searchTxt) {

        List<PayAmount> payAmounts = new ArrayList<>();

        String sql = "SELECT pa.id,pa.class_id,c.name AS class_name, pa.section_id ,s.section AS section_name, pa.teacher_amount, pa.student_amount FROM pay_amount AS pa JOIN classes AS c JOIN sections AS s ON c.id=pa.class_id AND s.id=pa.section_id WHERE c.name LIKE '%"+ searchTxt + "%'";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ClassEntity classEntity = new ClassEntity(resultSet.getInt("class_id"),0, resultSet.getString("class_name"));
                Section section = new Section(resultSet.getInt("section_id"),resultSet.getString("section_name"));
                payAmounts.add(
                        new PayAmount(
                                resultSet.getInt("id"),
                                classEntity,
                                section,
                                resultSet.getInt("teacher_amount"),
                                resultSet.getInt("student_amount")
                        )
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,resultSet);
        return payAmounts;

    }
}
