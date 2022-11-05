package kz.lakida.javacourse.database;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserBalanceRepository {

    private final Connection connection;

    public UserBalanceRepository(Connection connection) {
        this.connection = connection;
    }

    public Map<String, BigDecimal> findAllBalancesByUser() {

        Map<String, BigDecimal> result = new HashMap<>();

        try {
            String sql = "SELECT * u.name , sum(a.balance) "
                    + "FROM users u "
                    + "LEFT JOIN accounts a ON(a.user_id = u.id) "
                    + "GROUP BY u.id";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getBigDecimal(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void initTables() {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE users("
                    +"id UUID PRIMARY KEY,"
                    +"name VARCHAR(32)"
                    +");";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE accounts("
                    +"id UUID PRIMARY KEY,"
                    +"user_id NOT NULL REFERENCES users(id),"
                    +"balance DECIMAL(10,2) NOT NULL DEFAULT 0.0"
                    +");";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
