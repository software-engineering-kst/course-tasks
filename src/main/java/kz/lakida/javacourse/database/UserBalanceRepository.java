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
        Statement statement = connection.createStatement();
        String SQL_SELECT = "select u.name_user,sum(a.balance) from users u left join accounts a on (a.user_id = u.id) group by u.id";
        ResultSet result = statement.executeQuery(SQL_SELECT);
        while (result.next()){
           map.put(result.getString(1), result.getBigDecimal(2));
        }
        return map;
    }

    public void initTables() throws SQLException {
        //connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        Statement statement = connection.createStatement();
        String SQL_CREATE_USER = "create TABLE users(id uuid primary key, name_user VARCHAR ( 255 ) NOT NULL);";
        String SQL_CREATE_ACCOUNTS = "create TABLE accounts(id uuid primary key, user_id UUID not null references users(id), balance decimal(10,2) not null default 0.0);";

        //statement.execute(SQL_CREATE_USER);
        //statement.execute(SQL_CREATE_ACCOUNTS);
        statement.executeUpdate(SQL_CREATE_USER);
        statement.executeUpdate(SQL_CREATE_ACCOUNTS);

        UUID kuanyshId = UUID.randomUUID();
        createUser(kuanyshId, "Kuanysh");
        createAccount(kuanyshId, BigDecimal.valueOf(250000.00));
        createAccount(kuanyshId, BigDecimal.valueOf(100000.50));

        UUID denisId = UUID.randomUUID();
        createUser(denisId, "Denis");
        createAccount(denisId, BigDecimal.valueOf(240000.15));
        createAccount(denisId, BigDecimal.valueOf(120000.60));


    }

    private void createUser(UUID id, String name) throws SQLException {
        var statement = connection.prepareStatement("INSERT INTO \"users\" (\"id\", \"name\") VALUES (?, ?);");
        statement.setObject(1, id, Types.OTHER);
        statement.setString(2, name);
        statement.executeUpdate();
    }

    private void createAccount(UUID userId, BigDecimal amount) throws SQLException {
        var statement = connection.prepareStatement("INSERT INTO \"accounts\" (\"id\", \"user_id\", \"balance\") VALUES (gen_random_uuid(), ?, ?);");
        statement.setObject(1, userId, Types.OTHER);
        statement.setObject(2, amount, Types.DECIMAL);
        statement.executeUpdate();
    }


}
