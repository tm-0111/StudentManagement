package raisetech.student.management.domein;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;


@Getter
@Setter
public class StudentDetail {


  private long id;
  private String name;


  private Student student;
  private List<StudentCourses> studentCourses = new ArrayList<>(); // 初期化

  public StudentDetail(Student student, List<StudentCourses> studentCourses) {
    this.student = student;
    this.studentCourses = studentCourses != null ? studentCourses : new ArrayList<>();
  }

  public StudentDetail() {

  }
}
