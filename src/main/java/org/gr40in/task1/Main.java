package org.gr40in.task1;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.naming.Context;
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
        String s = "Fucj";

        System.out.println(s);
        JAXBContext context = JAXBContext.newInstance(Student.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(studentList, new File(s));


//        //JSON
//        ListOfStudentConvertor.saveToJson(studentList);
//        System.out.println(ListOfStudentConvertor.loadFromJson(Student.class));
//
//        //BIN
//        String binFileName = "Student.bin";
//        ListOfStudentConvertor.toBinary(binFileName, studentList);
//        System.out.println(ListOfStudentConvertor.fromBinary(binFileName));
//
//        //XML
//        String xmlFileName = "Student.xml";
//        ListOfStudentConvertor.toXml(xmlFileName, studentList);
//        System.out.println(ListOfStudentConvertor.fromXml(xmlFileName, Student.class));

    }


}