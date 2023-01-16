package kz.lakida.javacourse.tishuk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.UUID;

public class EntityControllerJava {
    private Connection connection; // public был

    public EntityControllerJava(Connection connection) {
        this.connection = connection; //this добавил
    }
    public synchronized void saveEntity(Entity entity) throws Exception {

        if (validate(entity)) { // убрал "!". переделал validate на true.
            System.out.println("start"); // перенес сюда эту строку
            String requestBD = "INSERT INTO entity (id, name) values (?, ?)"; // создал отдельную строку запроса
            try (PreparedStatement statement = connection.prepareStatement(requestBD)) { // переписал на PreparedStatement
                 statement.setObject(1, entity.getId(), Types.OTHER);
                 statement.setString(2, entity.getName());
                 statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException("No save", e); //  LOGGER.error было так у меня. ругалось
            }
            System.out.println("finish");
        } else { // else добавил
            throw new RuntimeException("No valid");
        }
    }

    public void delete(Entity entity) throws Exception {

        System.out.println("start");
        try (Statement statement = connection.createStatement()){
             boolean execute = statement.execute("DELETE FROM entity WHERE id=" + entity.getId()); //было getName
        } catch (SQLException e) {
            throw new RuntimeException("No deleted", e);
        }
            System.out.println("finish");
    }

    public boolean validate(Entity entity) {
        //some code here
        return true;
    }

    public class Entity{
        public Entity(UUID id, String name) { // создал конструктор, т.к
            this.id = id;
            this.name = name;
        }

        private UUID id; // был String
        private String name;

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}