package by.clevertec.check.dao;

import by.clevertec.check.entity.DiscountCard;
import by.clevertec.check.jdbc.PropertiesManager;

import java.sql.*;
import java.util.Objects;

public class DiscountCardDao {
    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASS_KEY = "db.password";

    public static final String SQL_SELECT_ITEM_BY_ID = "SELECT * FROM item_storage.discount_card WHERE item_id=?";


    public DiscountCard getCardById(String itemId) {
        Connection connection = null;
        PreparedStatement statement;
        DiscountCard card = null;
        try {
            connection = DriverManager.getConnection(
                    PropertiesManager.getPropertyByKey(DB_URL_KEY),
                    PropertiesManager.getPropertyByKey(DB_USERNAME_KEY),
                    PropertiesManager.getPropertyByKey(DB_PASS_KEY));
            statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_ID);
            statement.setString(1, itemId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                card = DiscountCard.builder().
                        id(resultSet.getInt("id")).
                        itemId(resultSet.getString("item_id")).
                        name(resultSet.getString("name")).
                        discount(resultSet.getDouble("discount"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(connection)) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return card;
    }
}
