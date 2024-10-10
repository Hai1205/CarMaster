package DAO.Property;

import DAO.Database;
import DTO.Property.StyleDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StyleDAO {
    public static void insert(StyleDTO stDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "INSERT INTO style (styleID, styleName) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, stDTO.getStyleID());
            pstmt.setString(2, stDTO.getStyleName());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(StyleDTO stDTO) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = (Connection) Database.getConnection();
            String sql = "UPDATE style SET styleName = ? WHERE styleID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, stDTO.getStyleName());
            pstmt.setString(2, stDTO.getStyleID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<StyleDTO> getList() {
        ArrayList<StyleDTO> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT * FROM style";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String styleID = resultSet.getString("styleID");
                String styleName = resultSet.getString("styleName");
                list.add(new StyleDTO(styleID, styleName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String[] getListStyleName() {
        ArrayList<String> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getConnection();
            String query = "SELECT styleName FROM style";
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                list.add(resultSet.getString("styleName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list.toArray(new String[0]);
    }

    public static String getIDByName(String styleName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String styleID = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT styleID FROM style WHERE styleName = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, styleName);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                styleID = resultSet.getString("styleID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return styleID;
    }

    public static String getNameByID(String styleID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String styleName = null;
        try {
            connection = Database.getConnection();
            String query;
            query = "SELECT styleName FROM style WHERE styleID = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, styleID);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                styleName = resultSet.getString("styleName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return styleName;
    }
}
