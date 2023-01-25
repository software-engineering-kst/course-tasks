package kz.lakida.javacourse.correctoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class PropertyLoader {
     private List<Property> properties = new LinkedList<>();
    private final String path;
    public PropertyLoader(String path){
        this.path = path;
    }

    public void loadProperties() {
        File file = new File(path);
        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            String line;
            while ((line = r.readLine()) != null) {
                //решил использовать токены
                StringTokenizer stringTokenizer = new StringTokenizer(line,":");
                String n = stringTokenizer.nextToken();
                String v = stringTokenizer.nextToken();
                Property p = new Property(n, v);
                properties.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    переделал метод getValue
    public String getValue(String key) {
            Property property;
            for(Property property1 : properties){
                if (property1.getKey().equals(key)){
                    return property1.getValue();
                }
        }
        return null;
    }
}
 class Property {
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

        @Override
        public String toString ()   {
            return key + "=" + value;
        }
    }

