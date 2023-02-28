package grades;

public class Grade {
    private String teacher;
    private Student student;
    private Homework homework;
    private double value;

    @Override
    public String toString() {
        return "Grade{" +
                "teacher='" + teacher + '\'' +
                ", student=" + student +
                ", homework=" + homework +
                ", value=" + value +
                '}';
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Grade(String teacher, Student student, Homework homework, double value) {
        this.teacher = teacher;
        this.student = student;
        this.homework = homework;
        this.value = value;
    }
}
