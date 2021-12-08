package school.models.section;

import school.helper.DBConnection;
import school.models.section.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionService {

    public List<Section> getAllSections() {
        List<Section> sections = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM sections";

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                sections.add(new Section(
                        resultSet.getInt("id"),
                        resultSet.getString("section")
                        ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        DBConnection.closeConnection(connection,ps,resultSet);
        return sections;
    }
}
