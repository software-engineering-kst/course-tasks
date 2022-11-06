package kz.lakida.javacourse.database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserBalanceRepository {
    private Connection connection;

    public UserBalanceRepository(Connection connection) {
        this.connection = connection;
    }

    public Map<String, BigDecimal> findAllBalancesByUser() throws SQLException {
        Map<String, BigDecimal> map = new HashMap<>();
        Statement statement = this.connection.createStatement();
        String SQL_SELECT = "select u.name,sum(a.balance) from users u left join accounts a on (a.user_id = u.id) group by u.id";
        ResultSet result = statement.executeQuery(SQL_SELECT);
        while (result.next()){
           map.put(result.getString("name"), result.getBigDecimal("sum"));
        }
        statement.close();
        return map;
    }

    public void initTables() throws SQLException {
        Statement statement = this.connection.createStatement();
        String SQL_CREATE_USER = "create TABLE IF NOT EXISTS users (id uuid primary key, name VARCHAR ( 255 ) NOT NULL);";
        String SQL_CREATE_ACCOUNTS = "create TABLE IF NOT EXISTS accounts (id uuid primary key, user_id UUID not null references users(id), balance decimal(10,2) not null default 0.0);";

        statement.executeUpdate("drop table users CASCADE");
        statement.executeUpdate("drop table accounts CASCADE");
        statement.executeUpdate(SQL_CREATE_USER);
        statement.executeUpdate(SQL_CREATE_ACCOUNTS);
        
        statement.close();

    }


}
