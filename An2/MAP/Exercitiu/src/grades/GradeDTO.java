package grades;

public class GradeDTO {
    private double grade;
    private String studentName;
    private String homeworkId;

    private String teacher;

    @Override
    public String toString() {
        return "GradeDTO{" +
                "grade=" + grade +
                ", studentName='" + studentName + '\'' +
                ", homeworkId='" + homeworkId + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public GradeDTO(double grade, String studentName, String homeworkId, String teacher) {
        this.grade = grade;
        this.studentName = studentName;
        this.homeworkId = homeworkId;
        this.teacher = teacher;
    }
}
