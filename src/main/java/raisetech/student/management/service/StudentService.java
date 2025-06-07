package raisetech.student.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

    private StudentRepository repository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository ,StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     * @return　受講生一覧（全件）
     */

    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentCourses> coursesList = repository.searchStudentCoursesList();
        return converter.convertStudentDetails(studentList, coursesList);
    }

    /**
     * 受講生検索です。
     *IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     * @param id　受講生ID
     * @return　受講生
     */

    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentCourses> studentCourses = repository.searchStudentCourses(student.getId());
        return new StudentDetail(student,studentCourses);
    }

    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        repository.registerStudent(studentDetail.getStudent());
        for (StudentCourses studentCourse : studentDetail.getStudentCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setCourseStartAt(LocalDateTime.now());
            studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
            repository.registerStudentCourse(studentCourse);
        }
        return studentDetail;
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
                studentCourse.setCourseStartAt(LocalDateTime.now());
                studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
                repository.registerStudentCourse(studentCourse);
            }
        }

    }
}