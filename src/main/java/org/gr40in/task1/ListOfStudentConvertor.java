package org.gr40in.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.sql.rowset.spi.XmlWriter;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.XMLFormatter;

public class ListOfStudentConvertor {


    // region JSON
    private static final Type typeOfData = new TypeToken<ArrayList<Student>>() {
    }.getType();

    public static void saveToJson(List<?> list) throws IOException {
        if (list.isEmpty()) return;
        var fileName = list.get(0).getClass().getSimpleName() + ".json";
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
    public static void toXml(String fileName,  List<?> list) throws IOException {
//                Collection<String> list2 = new ArrayList<>();
//        list2.add("one");
//        list2.add("too");
//        list2.add("tree");
        XmlMapper mapper = new XmlMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        mapper.findAndRegisterModules();

//        XmlMapper mapper = XmlMapper.xmlBuilder()
//                .findAndAddModules()
//                .addModule(new JavaTimeModule())
//                .configure(SerializationFeature.INDENT_OUTPUT, true)
//                .build();
        mapper.writeValue(new File(fileName), list);
    }

    public static List<?> fromXml(String fileName, Class<?> classOfItemsInList) throws IOException {

        File file = new File(fileName);
        if (!file.exists()) return null;

        XmlMapper mapper = XmlMapper.builder()
                .findAndAddModules()
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.INDENT_OUTPUT, true)

                .build();

        return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, classOfItemsInList));
    }

    //endregion

    //region Binary
    public static void toBinary(String fileName, List<?> list) {
        try (var file = new FileOutputStream(fileName);
             var out = new ObjectOutputStream(file)) {
            out.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<?> fromBinary(String fileName) {
        try (var file = new FileInputStream(fileName);
             var in = new ObjectInputStream(file)) {
            var someStudent = in.readObject();
            return (List<?>) someStudent;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion


}
