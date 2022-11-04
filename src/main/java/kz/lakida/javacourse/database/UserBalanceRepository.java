package kz.lakida.javacourse.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collections;
import java.util.Map;

public class UserBalanceRepository {
    public UserBalanceRepository(Connection connection) {

    }

    public Map<String, BigDecimal> findAllBalancesByUser() {
        throw new UnsupportedOperationException();
    }

    public void initTables() {
        throw new UnsupportedOperationException();
    }
}
