package raisetech.student.management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.domein.StudentDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before() {
        sut = new StudentService(repository, converter);
    }

    @Test
    void 受講生詳細の登録_初期化処理が行われること(){
        String id = "999";
        Student student = new Student();
        student.setId(id);
        StudentCourse studentCourse = new StudentCourse();

        sut.initStudentsCourse(studentCourse, student);

        assertEquals(id, studentCourse.getStudentId());
        assertEquals(LocalDateTime.now().getHour(),studentCourse.getCourseStartAt().getHour());
        assertEquals(LocalDateTime.now().plusYears(1).getYear(), studentCourse.getCourseEndAt().getYear());
    }

    @Test
    void 受講生一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
        //事前準備
        StudentService sut = new StudentService(repository, converter);
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        //実行
        List<StudentDetail> actual = sut.searchStudentList();

        verify(repository, times(1)).search();
        verify(repository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList, studentCourseList);
    }

    //Studentクラス
    @Test
    void 受講生検索_ID指定で正しい詳細情報が返ること() {
        Student student = new Student();
        student.setId("1");
        List<StudentCourse> courses = List.of(new StudentCourse());

        when(repository.searchStudent("1")).thenReturn(student);
        when(repository.searchStudentCourse("1")).thenReturn(courses);

        StudentDetail result = sut.searchStudent("1");

        verify(repository).searchStudent("1");
        verify(repository).searchStudentCourse("1");
        Assertions.assertNotNull(result);
    }

    //registerStudent
    @Test
    void 受講生登録_学生情報とコース情報が保存されること() {
        Student student = new Student();
        student.setId("100");
        student.setName("tanaka");

        StudentCourse course1 = new StudentCourse();
        StudentCourse course2 = new StudentCourse();

        List<StudentCourse> courseList = List.of(course1, course2);

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourseList(courseList);

        StudentDetail result = sut.registerStudent(detail);

        verify(repository, times(1)).registerStudent(student);
        verify(repository, times(1)).registerStudentCourse(course1);
        verify(repository, times(1)).registerStudentCourse(course2);

        Assertions.assertNotNull(result);
    }

    @Test
    void 受講生更新_更新成功メッセージが返ること() {
        StudentDetail detail = new StudentDetail();
        Student student = new Student();
        StudentCourse course = new StudentCourse();

        detail.setStudent(student);
        detail.setStudentCourseList(List.of(course));

        sut.updateStudent(detail);

        verify(repository).updateStudent(student);
        verify(repository).updateStudentCourse(course);
    }
}