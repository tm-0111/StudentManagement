package raisetech.student.management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.Repository.StudentCoursesRepository;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentCoursesRepository coursesRepository;

  @Autowired
  public StudentService(StudentRepository repository, StudentCoursesRepository coursesRepository) {
    this.repository = repository;
    this.coursesRepository = coursesRepository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourses> searchStudentCourseList() {
    return coursesRepository.searchStudentCourses();

  }

  @Transactional
  public void registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setStart_Date(LocalDateTime.now());
      studentCourse.setEnd_Date(LocalDateTime.now().plusYears(1));
      coursesRepository.registerStudentCourse(studentCourse);
    }
  }

  @Transactional
  public StudentDetail getStudentDetailById(Long id) {
    Student student = repository.findById(id);
    List<StudentCourses> studentCourses = coursesRepository.findByStudentId(id);

    return new StudentDetail(student, studentCourses);

  }


  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    //学生情報を更新
    repository.updateStudent(studentDetail.getStudent());
    //古いコースを削除
    coursesRepository.deleteByStudentId(studentDetail.getStudent().getId());
    //新しいコースを追加
    if (studentDetail.getStudentCourses() != null && !studentDetail.getStudentCourses().isEmpty()) {
      for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
        studentCourse.setStudentId(studentDetail.getStudent().getId());
        studentCourse.setStart_Date(LocalDateTime.now());
        studentCourse.setEnd_Date(LocalDateTime.now().plusYears(1));
        coursesRepository.registerStudentCourse(studentCourse);
      }
    }
  }
}