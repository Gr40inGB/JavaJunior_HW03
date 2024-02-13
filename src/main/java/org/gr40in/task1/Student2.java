package org.gr40in.task1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Student2 implements Serializable {
    private String name;
    transient double gpa;

    public Student2() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gpa=" + gpa +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Student2 student)) return false;
        return Double.compare(gpa, student.gpa) == 0 && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gpa);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }


    public Student2(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }
}
