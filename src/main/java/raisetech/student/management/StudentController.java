package raisetech.student.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentCourseList")
  public List<StudentCourses> getStudentCourseList() {
    return service.searchStudentCourseList();
  }

  @GetMapping("/students/30s")
  public List<Student> searchStudentsInTheir30s() {
    return service.searchStudentsInTheir30s();
  }

  @GetMapping("/studentCourses/Java")
  public List<StudentCourses> searchJavaCourses() {
    return service.searchJavaCourses();
  }
}