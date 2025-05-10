package raisetech.student.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourses> searchStudentCourseList() {
    return repository.searchStudentCourses();

  }

  public void insertStudentWithCourses(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.saveStudent(student);  //学生保存

    StudentCourses course = studentDetail.getStudentCourses();
    if (course != null && course.getCourseName() != null && !course.getCourseName().isBlank()) {
      course.setStudent_id(student.getId());  // 外部キー
      repository.saveStudentCourse(course);  //コース保存
    }
  }
}