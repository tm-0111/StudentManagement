package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

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

    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentCourses(studentCourses);
        return studentDetail;
    }


    public List<StudentCourses> searchStudentCourseList() {
        return repository.searchStudentCoursesList();

    }

    @Transactional
    public void registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setStartDate(LocalDateTime.now());
            studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourse(studentCourse);
        }
    }


    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        //学生情報を更新
        repository.updateStudent(studentDetail.getStudent());
        //古いコースを削除
       // repository.deleteByStudentId(studentDetail.getStudent().getId());
        //新しいコースを追加
        if (studentDetail.getStudentCourses() != null && !studentDetail.getStudentCourses().isEmpty()) {
            for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
                studentCourse.setStudentId(studentDetail.getStudent().getId());
                studentCourse.setStartDate(LocalDateTime.now());
                studentCourse.setEndDate(LocalDateTime.now().plusYears(1));
                repository.registerStudentCourse(studentCourse);
            }
        }

    }
}