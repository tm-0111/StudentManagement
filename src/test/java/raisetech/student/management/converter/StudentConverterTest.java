package raisetech.student.management.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.domein.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentConverterTest {

    private StudentConverter sut;

    @BeforeEach
    void befor(){
        sut = new StudentConverter();
    }

    @Test
    void 受講生のリストと受講生コース情報のリストが作成できること(){
        Student student = createStudent();

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId("1");
        studentCourse.setStudentId("1");
        studentCourse.setCourseName("Javaコース");
        studentCourse.setCourseStartAt(LocalDateTime.now());
    studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);
    }

    @Test
    void 受講生リストと受講生コース情報のリストとを渡したときに紐づかない受講生コース情報は除外されること(){
        Student student = createStudent();

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId("1");
        studentCourse.setStudentId("2");
        studentCourse.setCourseName("Javaコース");
        studentCourse.setCourseStartAt(LocalDateTime.now());
        studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList =List.of(studentCourse);

        List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

        assertThat(actual.get(0).getStudent()).isEqualTo(student);
        assertThat(actual.get(0).getStudentCourseList()).isEmpty();
    }
    private static Student createStudent() {
        Student student = new Student();
        student.setId("1");
        student.setName("山田太郎");
        student.setKanaName("ヤマダタロウ");
        student.setNickname("タロー");
        student.setEmail("taro@example.com");
        student.setArea("東京");
        student.setAge(25);
        student.setSex("男性");
        student.setRemark("");
        student.setDeleted(false);
        return student;
    }
}
