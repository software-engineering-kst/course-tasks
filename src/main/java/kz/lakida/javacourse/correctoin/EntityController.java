package kz.lakida.javacourse.correctoin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

class EntityControllerJava {
    private final Connection connection;

    //подправлю конструктор добавлю this
    public EntityControllerJava(Connection connection) {
        this.connection = connection;
    }

    public synchronized void saveEntity(Entity entity) throws Exception {
        System.out.println("start");

        if (!validate(entity)) {
            throw new RuntimeException("No valid");
        }
        //этот код не безпасный
        //boolean execute = statement.execute("INSERT INTO entity (id, name) values (" + entity.getId() + ", " + entity.getName() + ")");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO entity (id, name) values (?, ?)");
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        statement.executeUpdate();

        //закрываю потоки
        try {
            statement.close();
            connection.close();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("finish");
        }
    }

    public void delete(Entity entity) throws Exception {
        System.out.println("start");

        Statement statement = connection.createStatement();
        //entity.getName() заменил на entity.getId()
        boolean execute = statement.execute("DELETE FROM entity WHERE id=" + entity.getId());
        if (!execute) {
            throw new IllegalStateException("No deleted");
        }

        System.out.println("finish");
    }

    public boolean validate(Entity entity) {
        //some code here
        return false;
    }

    public class Entity {
        //сделал поля final
        //id сделал типа int
        private final int id;
        private final String name;

        //добавил конструктор
        public Entity(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
