package org.gr40in.task1;

import com.google.gson.annotations.SerializedName;
import jdk.jfr.DataAmount;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Student implements Serializable {
    private String name;
    @DataAmount
    private LocalDate birthDate;
    transient double gpa;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gpa=" + gpa +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Student student)) return false;
        return Double.compare(gpa, student.gpa) == 0 && Objects.equals(name, student.name) && Objects.equals(birthDate, student.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, gpa);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }


    public Student(String name, LocalDate birthDate, double gpa) {
        this.name = name;
        this.birthDate = birthDate;
        this.gpa = gpa;
    }
}
