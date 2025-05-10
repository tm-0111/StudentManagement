package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudent_id(studentDetail.getStudent().getId());
      studentCourse.setStart_date(LocalDateTime.now());
      studentCourse.setEnd_date(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourse(studentCourse);
    }
  }
}