package kz.lakida.javacourse.tishuk;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PropertyLoader {
    private LinkedList<Property> properties = new LinkedList<>(); // решил оставить эту структуру данных, в случае записи свойств, чтоб они были в такой же последовательности

    public void loadProperties() {
        File file = new File("config.properties");
        try (BufferedReader r = new BufferedReader(new FileReader(file))) { //resourse
            String line;
            while ((line = r.readLine()) != null) {
                String[] words = line.split(":");
                if(words.length == 2) {
                    Property p = new Property(words[0], words[1]);
                    properties.add(p);
                } else {
                    throw new RuntimeException("Incorrect file structure");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getValue(String key) { // ключей может быть несколько
        List<String> listValue = new ArrayList<>();
        for(Property property : properties) {
            if (property.key.equals(key))  {
                listValue.add(property.value);
            }
        }
        return listValue;
    }

    private class Property {
        private String key;
        private String value;

        public Property(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}