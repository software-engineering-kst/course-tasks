package kz.lakida.javacourse.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserBalanceRepositoryTest {
    private Connection dbConnection;
    private UserBalanceRepository repository;

    @BeforeEach
    void initializeConnection() throws SQLException {
        dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
        repository = new UserBalanceRepository(dbConnection);
        repository.initTables();
        cleanData();
    }

    @AfterEach
    void closeConnection() throws SQLException {
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    @Test
    void shouldCalculateTotalBalancePerUser() throws SQLException {
        UUID kuanyshId = UUID.randomUUID();
        createUser(kuanyshId, "Kuanysh");
        createAccount(kuanyshId, BigDecimal.valueOf(250000.00));
        createAccount(kuanyshId, BigDecimal.valueOf(100000.50));

        UUID denisId = UUID.randomUUID();
        createUser(denisId, "Denis");
        createAccount(denisId, BigDecimal.valueOf(240000.15));
        createAccount(denisId, BigDecimal.valueOf(120000.60));

        var result = repository.findAllBalancesByUser();

        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.of(
                "Kuanysh", BigDecimal.valueOf(350000.50),
                "Denis", BigDecimal.valueOf(360000.75)
        ));
    }

    private void cleanData() throws SQLException {
        var statement = dbConnection.createStatement();
        statement.execute("DELETE FROM accounts;");
        statement.execute("DELETE FROM users;");
    }

    private void createUser(UUID id, String name) throws SQLException {
        var statement = dbConnection.prepareStatement("INSERT INTO \"users\" (\"id\", \"name\") VALUES (?, ?);");
        statement.setObject(1, id, Types.OTHER);
        statement.setString(2, name);
        statement.execute();
    }

    private void createAccount(UUID userId, BigDecimal amount) throws SQLException {
        var statement = dbConnection.prepareStatement("INSERT INTO \"accounts\" (\"id\", \"user_id\", \"balance\") VALUES (gen_random_uuid(), ?, ?);");
        statement.setObject(1, userId, Types.OTHER);
        statement.setObject(2, amount, Types.DECIMAL);
        statement.execute();
    }
}
