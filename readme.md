[ListOfStudentConvertor.java](src/main/java/org/gr40in/task1/ListOfStudentConvertor.java)
```java
package org.gr40in.task1;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListOfStudentConvertor {


    // region JSON
    private static final Type typeOfData = new TypeToken<ArrayList<Student>>() {
    }.getType();

    public static void saveToJson(List<?> list) throws IOException {
        if (list.isEmpty()) return;
        var fileName = list.get(0)
                .getClass()
                .getSimpleName() + ".json";
        Files.writeString(Path.of(fileName), getJson(list));
    }


    public static String getJson(List<?> list) throws IOException {
        String simpleName = list.getClass().getSimpleName();
        System.out.println(simpleName);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        return gson.toJson(list, typeOfData);
    }

    public static List<?> loadFromJson(Class<?> classOfData) throws IOException {
        var fileName = classOfData.getSimpleName() + ".json";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        return gson.fromJson(new FileReader(fileName), typeOfData);
    }
    //endregion

    //region XML
    public static void toXml(String fileName, List<?> list) throws IOException {
        XmlMapper mapper = XmlMapper.builder()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .addModule(new JavaTimeModule())
                .build();
        mapper.writeValue(new File(fileName), list);
    }

    public static List<?> fromXml(String fileName, Class<?> classOfItemsInList) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) return null;

        XmlMapper mapper = XmlMapper.builder()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .build();
        return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, classOfItemsInList));
    }

    //endregion

    //region Binary
    public static void toBinary(String fileName, List<?> list) {
        try (var file = new FileOutputStream(fileName); var out = new ObjectOutputStream(file)) {
            out.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<?> fromBinary(String fileName) {
        try (var file = new FileInputStream(fileName); var in = new ObjectInputStream(file)) {
            var someStudent = in.readObject();
            return (List<?>) someStudent;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}

}
```
[Main.java](src/main/java/org/gr40in/task1/Main.java)

```java
package org.gr40in.task1;

import javax.xml.bind.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
        Student garryPotter = new Student("Garry Potter", LocalDate.of(1980, 5, 31), 4.9);
        Student hermioneGranger = new Student("Hermione Granger", LocalDate.of(1979, 9, 19), 5.0);
        Student ronWeasley = new Student("Ron Weasley", LocalDate.of(1980, 3, 1), 3.9);

        List<Student> studentList = new ArrayList<>() {{
            add(garryPotter);
            add(hermioneGranger);
            add(ronWeasley);
        }};

        //JSON
        ListOfStudentConvertor.saveToJson(studentList);
        System.out.println(ListOfStudentConvertor.loadFromJson(Student.class));

        //BIN
        String binFileName = "Student.bin";
        ListOfStudentConvertor.toBinary(binFileName, studentList);
        System.out.println(ListOfStudentConvertor.fromBinary(binFileName));

        //XML
        String xmlFileName = "Student.xml";
        ListOfStudentConvertor.toXml(xmlFileName, studentList);
        System.out.println(ListOfStudentConvertor.fromXml(xmlFileName, Student.class));
    }
}
```

```
"C:\Program Files\Java\jdk-21\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2023.3.2\lib\idea_rt.jar=14107:C:\Program Files\JetBrains\IntelliJ IDEA 2023.3.2\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\Richard\Desktop\st\JavaJunior_HW03\target\classes;C:\Users\Richard\.m2\repository\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar;C:\Users\Richard\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.16.0\jackson-databind-2.16.0.jar;C:\Users\Richard\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.16.0\jackson-annotations-2.16.0.jar;C:\Users\Richard\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.16.0\jackson-core-2.16.0.jar;C:\Users\Richard\.m2\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-xml\2.16.1\jackson-dataformat-xml-2.16.1.jar;C:\Users\Richard\.m2\repository\org\codehaus\woodstox\stax2-api\4.2.1\stax2-api-4.2.1.jar;C:\Users\Richard\.m2\repository\com\fasterxml\woodstox\woodstox-core\6.5.1\woodstox-core-6.5.1.jar;C:\Users\Richard\.m2\repository\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.16.1\jackson-datatype-jsr310-2.16.1.jar;C:\Users\Richard\.m2\repository\javax\xml\bind\jaxb-api\2.3.1\jaxb-api-2.3.1.jar;C:\Users\Richard\.m2\repository\javax\activation\javax.activation-api\1.2.0\javax.activation-api-1.2.0.jar;C:\Users\Richard\.m2\repository\javax\activation\activation\1.1\activation-1.1.jar;C:\Users\Richard\.m2\repository\org\glassfish\jaxb\jaxb-runtime\2.3.1\jaxb-runtime-2.3.1.jar;C:\Users\Richard\.m2\repository\org\glassfish\jaxb\txw2\2.3.1\txw2-2.3.1.jar;C:\Users\Richard\.m2\repository\com\sun\istack\istack-commons-runtime\3.0.7\istack-commons-runtime-3.0.7.jar;C:\Users\Richard\.m2\repository\org\jvnet\staxex\stax-ex\1.8\stax-ex-1.8.jar;C:\Users\Richard\.m2\repository\com\sun\xml\fastinfoset\FastInfoset\1.2.15\FastInfoset-1.2.15.jar org.gr40in.task1.Main
[Student{name='Garry Potter', birthDate=1980-05-31, gpa=0.0}, Student{name='Hermione Granger', birthDate=1979-09-19, gpa=0.0}, Student{name='Ron Weasley', birthDate=1980-03-01, gpa=0.0}]
[Student{name='Garry Potter', birthDate=1980-05-31, gpa=0.0}, Student{name='Hermione Granger', birthDate=1979-09-19, gpa=0.0}, Student{name='Ron Weasley', birthDate=1980-03-01, gpa=0.0}]
[Student{name='Garry Potter', birthDate=1980-05-31, gpa=0.0}, Student{name='Hermione Granger', birthDate=1979-09-19, gpa=0.0}, Student{name='Ron Weasley', birthDate=1980-03-01, gpa=0.0}]

Process finished with exit code 0

```
