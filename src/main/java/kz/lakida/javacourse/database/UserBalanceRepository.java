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
            String sql = "SELECT * u.name , sum(a.balances) "
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
        throw new UnsupportedOperationException();
    }
}
