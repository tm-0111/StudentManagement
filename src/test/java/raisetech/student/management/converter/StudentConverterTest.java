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
    void befor() {
        sut = new StudentConverter();
    }

    @Test
    void 受講生のリストと受講生コース情報のリストが作成できること() {
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
    void 受講生リストと受講生コース情報のリストとを渡したときに紐づかない受講生コース情報は除外されること() {
        Student student = createStudent();

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId("1");
        studentCourse.setStudentId("2");
        studentCourse.setCourseName("Javaコース");
        studentCourse.setCourseStartAt(LocalDateTime.now());
        studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));

        List<Student> studentList = List.of(student);
        List<StudentCourse> studentCourseList = List.of(studentCourse);

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

    @Test
    void 受講生とコースをマッピングできること() {

        Student student1 = new Student();
        student1.setId("1");
        student1.setName("山田太郎");

        Student student2 = new Student();
        student2.setId("3");
        student2.setName("佐藤花子");

        StudentCourse course1 = new StudentCourse();
        course1.setId("1");
        course1.setStudentId("1");
        course1.setCourseName("Javaコース");
        course1.setCourseStartAt(LocalDateTime.now().minusMonths(2));
        course1.setCourseEndAt(LocalDateTime.now());

        StudentCourse course2 = new StudentCourse();
        course2.setId("3");
        course2.setStudentId("3");
        course2.setCourseName("デザインコース");
        course2.setCourseStartAt(LocalDateTime.now().minusMonths(1));
        course2.setCourseEndAt(LocalDateTime.now());

        List<StudentDetail> result = sut.convertStudentDetails(
                List.of(student1, student2),
                List.of(course1, course2)
        );

        assertThat(result).hasSize(2);

        StudentDetail detail1 = result.get(0);
        assertThat(detail1.getStudent().getId()).isEqualTo("1");
        assertThat(detail1.getStudentCourseList()).hasSize(1);
        assertThat(detail1.getStudentCourseList().get(0).getCourseName()).isEqualTo("Javaコース");

        StudentDetail detail2 = result.get(1);
        assertThat(detail2.getStudent().getId()).isEqualTo("3");
        assertThat(detail2.getStudentCourseList()).hasSize(1);
        assertThat(detail2.getStudentCourseList().get(0).getCourseName()).isEqualTo("デザインコース");
    }

    @Test
    void コースがない場合でも空リストが返ること() {
        Student student = new Student();
        student.setId("999");
        student.setName("テストユーザー");

        List<StudentDetail> result = sut.convertStudentDetails(
                List.of(student),
                List.of()
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudent().getId()).isEqualTo("999");
        assertThat(result.get(0).getStudentCourseList()).isEmpty();
    }
}
