package utils.service;


import domain.Nota;
import domain.Student;
import domain.Tema;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;

public class ServiceManager {

    public  List<Student> findAllStudents() {
        Student s1 = new Student("andrei", 221);
        s1.setId(1l);
        Student s2 = new Student("dan", 222);
        s2.setId(2l);
        Student s3 = new Student("gigi", 221);
        s3.setId(3l);
        Student s4 = new Student("costel", 222);
        s4.setId(4l);
        Student s5 = new Student("vasile", 223);
        s4.setId(5l);
        Student s6 = new Student("mirel", 223);
        s4.setId(6l);
        Student s7 = new Student("vlad", 224);
        s7.setId(7l);
        Student s8 = new Student("matei", 225);
        s7.setId(8l);

        return Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8);
    }

    public  List<Tema> findAllHomeWorks() {
        return Arrays.asList(
                new Tema("t1", "desc1"),
                new Tema("t2", "desc2"),
                new Tema("t3", "desc3"),
                new Tema("t4", "desc4")
        );
    }

    private  List<Nota> getNote(List<Student> stud, List<Tema> teme) {
        return Arrays.asList(
                new Nota(stud.get(0), teme.get(0), 10d, LocalDate.of(2019, 11, 2), "profesor1"),
                new Nota(stud.get(1), teme.get(0), 9d, LocalDate.of(2019, 11, 2).minusWeeks(1), "profesor1"),
                new Nota(stud.get(1), teme.get(1), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(1), teme.get(2), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(2), teme.get(1), 7d, LocalDate.of(2019, 10, 28), "profesor1"),
                new Nota(stud.get(2), teme.get(3), 9d, LocalDate.of(2019, 10, 27), "profesor2"),
                new Nota(stud.get(3), teme.get(3), 10d, LocalDate.of(2019, 10, 29), "profesor2"),
                new Nota(stud.get(3), teme.get(2), 10d, LocalDate.of(2019, 10, 30), "profesor2"),
                new Nota(stud.get(4), teme.get(3), 8d, LocalDate.of(2019, 10, 29), "profesor1"),
                new Nota(stud.get(4), teme.get(2), 9d, LocalDate.of(2019, 10, 13), "profesor2"),
                new Nota(stud.get(5), teme.get(2), 10d, LocalDate.of(2019, 11, 29), "profesor1"),
                new Nota(stud.get(6), teme.get(3), 7d, LocalDate.of(2019, 11, 15), "profesor2"),
                new Nota(stud.get(6), teme.get(2), 10d, LocalDate.of(2019, 10, 29), "profesor1"),
                new Nota(stud.get(6), teme.get(1), 6d, LocalDate.of(2019, 11, 23), "profesor1"),
                new Nota(stud.get(7), teme.get(0), 10d, LocalDate.of(2019, 11, 18), "profesor2"),
                new Nota(stud.get(7), teme.get(2), 8d, LocalDate.of(2019, 11, 7), "profesor1"),
                new Nota(stud.get(7), teme.get(3), 9d, LocalDate.of(2019, 10, 29), "profesor1")
        );
    }

    public  List<Nota> findAllGrades() {
        return getNote(findAllStudents(),findAllHomeWorks());
    }


}
