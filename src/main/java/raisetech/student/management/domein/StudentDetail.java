package raisetech.student.management.domein;


import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;


@Getter
@Setter
public class StudentDetail {


  private Student student;
  private StudentCourses studentCoursesList;


}
