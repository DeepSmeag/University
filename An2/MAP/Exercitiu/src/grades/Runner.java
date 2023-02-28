package grades;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {
    public static void report1(List<Grade> gradeList){
        Predicate<Grade> byGroup = x -> x.getStudent().getGroup() == 249;
        Predicate<Grade> byTeacher = x -> x.getTeacher().equals("Suciu Dan");

        Predicate<Grade> filter = byGroup.and(byTeacher);
//        gradeList.stream()
//                .filter(filter)
//                .forEach(System.out::println);

        gradeList.stream()
                .filter(filter)
                .map(x -> new GradeDTO(x.getValue(), x.getStudent().getName(), x.getHomework().getId(), x.getTeacher()))
                .forEach(System.out::println);
    }

    public static void report2(List<Grade> gradeList){
        Map<Student, List<Grade>> studentListMap = gradeList.stream()
                .collect(Collectors.groupingBy(Grade::getStudent));
        studentListMap.entrySet()
                .forEach(x -> {
                    int count = x.getValue().size();
                    double sum = x.getValue().stream()
                                    .map(Grade::getValue)
                                    .reduce(0D, Double::sum);
                    System.out.println(x.getKey().getName() + " " + sum/count);
                });
    }

    public static void report3(List<Homework> hw, List<Grade> grades, String idTema) {
        // Create a report that shows average grades for a given homework id
        System.out.println(idTema + " " + grades.stream().filter(x -> x.getHomework().getId().equals(idTema)).
                collect(Collectors.averagingDouble(Grade::getValue)));

    }
    public static void report4(List<Homework> hw, List<Grade> grades) {
        // Create a report that shows the homework id + name with the biggest average grade
        System.out.println(grades.stream().collect(Collectors.groupingBy(x -> x.getHomework().getId(),
                        Collectors.averagingDouble(Grade::getValue)))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get());


    }
    public static void report5(List<Homework> hw, List<Grade> grades) {
        // Create a report that shows the homework id + name with the smallest average grade
        System.out.println(grades.stream().collect(Collectors.groupingBy(x -> x.getHomework().getId(),
                        Collectors.averagingDouble(Grade::getValue)))
                .entrySet().stream().min(Map.Entry.comparingByValue()).get());
    }

    public static void main(String[] args) {
        Student student1 = new Student("Marin Ionescu", 249);
        student1.setId(1L);
        Student student2 = new Student("Bianca Alexandrescu", 249);
        student2.setId(2L);
        Student student3 = new Student("Diana Stefanescu", 225);
        student3.setId(3L);
        Student student4 = new Student("Dan Mircea", 221);
        student4.setId(4L);
        List<Student> studentList = List.of(student1, student2, student3, student4);

        Homework homework1 = new Homework("Proiectare baza de date", "BD_L1");
        Homework homework2 = new Homework("Interogare baza de date", "BD_L2");
        Homework homework3 = new Homework("Prolog liste 1", "PLF_P1");
        Homework homework4 = new Homework("Prolog backtracking", "PLF_P3");
        List<Homework> homeworkList = List.of(homework1, homework2, homework3, homework4);

        Grade grade1 = new Grade("Suciu Dan", student1, homework1, 9.8);
        Grade grade2 = new Grade("Suciu Dan", student2, homework1, 7.8);
        Grade grade3 = new Grade("Mali Imre", student3, homework2, 9.89);
        Grade grade4 = new Grade("Mali Imre", student4, homework2, 7.8);
        Grade grade5 = new Grade("Suciu Dan", student4, homework1, 5.8);
        List<Grade> gradeList = List.of(grade1, grade2, grade3, grade4, grade5);
        report1(gradeList);
        report2(gradeList);
        report3(homeworkList, gradeList, "BD_L1");
        report4(homeworkList, gradeList);
        report5(homeworkList, gradeList);
    }
}
