package by.clevertec.check.dao;

import by.clevertec.check.entity.Item;
import by.clevertec.check.jdbc.PropertiesManager;

import java.sql.*;
import java.util.Objects;

public class CheckDao {

    public static final String DB_URL_KEY = "db.url";
    public static final String DB_USERNAME_KEY = "db.username";
    public static final String DB_PASS_KEY = "db.password";

    public static final String SQL_SELECT_ITEM_BY_ID = "SELECT * FROM item_storage.product WHERE id=?";


    public Item getItemById(int id) {
        Connection connection = null;
        PreparedStatement statement;
        Item item = null;
        try {
            connection = DriverManager.getConnection(
                    PropertiesManager.getPropertyByKey(DB_URL_KEY),
                    PropertiesManager.getPropertyByKey(DB_USERNAME_KEY),
                    PropertiesManager.getPropertyByKey(DB_PASS_KEY));
            statement = connection.prepareStatement(SQL_SELECT_ITEM_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item = Item.builder().
                        id(resultSet.getString("id")).
                        name(resultSet.getString("name")).
                        price(resultSet.getDouble("price")).
                        discount(resultSet.getDouble("discount")).
                        promotionalItem(resultSet.getBoolean("promotionalItem"))
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
        return item;
    }
}
