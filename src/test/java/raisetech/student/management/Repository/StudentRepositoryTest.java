package raisetech.student.management.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(5);

        Student student = actual.get(0);
        assertThat(student.getId()).isEqualTo("1");
        assertThat(student.getName()).isEqualTo("山田太郎");
        assertThat(student.getKanaName()).isEqualTo("ヤマダタロウ");
        assertThat(student.getNickname()).isEqualTo("タロ");
        assertThat(student.getEmail()).isEqualTo("taro@example.com");
        assertThat(student.getArea()).isEqualTo("東京");
        assertThat(student.getAge()).isEqualTo(25);
        assertThat(student.getSex()).isEqualTo("男性");
    }

    @Test
    void 受講生IDで検索が行えること() {
        Student actual = sut.searchStudent("1");

        Student student = new Student();
        student.setId("1");
        student.setName("山田太郎");
        student.setKanaName("ヤマダタロウ");
        student.setNickname("タロ");
        student.setEmail("taro@example.com");
        student.setArea("東京");
        student.setAge(25);
        student.setSex("男性");
        student.setRemark(null);
        student.setDeleted(false);

        assertThat(actual).isEqualTo(student);
    }

    @Test
    void 受講生コースの全件検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual.size()).isEqualTo(10);
    }

    @Test
    void 受講生IDによるコース検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourse("1");
        assertThat(actual).isNotNull();
        assertThat(actual).isNotNull();
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student(
                null,
                "山田太郎",
                "ヤマダタロウ",
                "タロ",
                "taro@example.com",
                "東京",
                25,
                "男性",
                "",
                false
        );
        sut.registerStudent(student);

        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(6);
    }

    @Test
    void 受講生コースの登録が行えること() {
        StudentCourse course = new StudentCourse();
        course.setStudentId("1");
        course.setCourseName("testCourse");
        course.setCourseStartAt(LocalDateTime.of(2025, 1, 1, 3, 3));
        course.setCourseEndAt(LocalDateTime.of(2025, 6, 30, 1, 1));

        sut.registerStudentCourse(course);

        List<StudentCourse> actual = sut.searchStudentCourseList();

        assertThat(actual.size()).isEqualTo(11);
    }

    @Test
    void 受講生コースの更新が行えること() {
        StudentCourse course = new StudentCourse();
        course.setStudentId("1");
        course.setCourseName("testCourse");
        course.setCourseStartAt(LocalDateTime.of(2025, 1, 1, 3, 3));
        course.setCourseEndAt(LocalDateTime.of(2025, 6, 30, 1, 1));

        sut.registerStudentCourse(course);

        course.setCourseName("newCourse");
        sut.updateStudentCourse(course);

        List<StudentCourse> actual = sut.searchStudentCourse("1");
        assertThat(actual.size()).isEqualTo(1);
    }
}