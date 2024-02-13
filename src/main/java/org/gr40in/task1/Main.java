package org.gr40in.task1;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Student garryPotter = new Student("Garry Potter", LocalDate.of(1980, 5, 31), 4.9);
        Student hermioneGranger = new Student("Hermione Granger", LocalDate.of(1979, 9, 19), 5.0);
        Student ronWeasley = new Student("Ron Weasley", LocalDate.of(1980, 3, 1), 3.9);

        List<Student> studentList = new ArrayList<>() {{
            add(garryPotter);
            add(hermioneGranger);
            add(ronWeasley);
        }};

//       ListOfStudentConvertor.saveToJson(studentList);
//       System.out.println(ListOfStudentConvertor.loadFromJson(Student.class));
//
//        String binFileName = "Student.bin";
//
//        ListOfStudentConvertor.toBinary(binFileName, studentList);
//        System.out.println(ListOfStudentConvertor.fromBinary(binFileName));


        String xmlFileName = "Student.xml";
        ListOfStudentConvertor.toXml(xmlFileName, studentList);
//
//        System.out.println(ListOfStudentConvertor.fromXml(xmlFileName,Student.class));
//        List<String> list = new ArrayList<>();
//        list.add("one");
//        list.add("too");
//        list.add("tree");
//
//        XmlMapper mapper = new XmlMapper();
//        mapper.writeValue(new File("testXML"),list);
    }


}