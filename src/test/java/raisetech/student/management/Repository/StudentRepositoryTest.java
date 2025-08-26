package raisetech.student.management.Repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.ApplicationStatus;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();

        assertThat(actual).hasSizeGreaterThanOrEqualTo(3);

        Student student1 = new Student("1", "山田太郎", "ヤマダタロウ", "タロ", "taro@example.com", "東京", 25, "男性", null, false);
        Student student2 = new Student("4", "佐藤良子", "サトウリョウコ", "リョウ", "ryoko@example.com", "福岡", 28, "女性", null, false);
        Student student3 = new Student("3", "田中花子", "タナカハナコ", "ハナ", "hana@example.com", "北海道", 22, "女性", null, false);

        assertThat(actual)
                .contains(student1, student2, student3);
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
        StudentCourse studentCourse = new StudentCourse(
                null,
                "1",
                "Javaコース",
                LocalDateTime.of(2023, 4, 1, 9, 0, 0),
                LocalDateTime.of(2023, 7, 1, 15, 0, 0),
                null,
                null,
                "PROVISIONAL"
        );

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse("1");

        assertThat(actual).isNotEmpty();
        assertThat(actual).anySatisfy(course ->
                assertThat(course.getCourseName()).isEqualTo("Javaコース")
        );
    }

    @Test
    void 受講生コースの更新が行えること() {
        StudentCourse studentCourse = new StudentCourse(
                null,
                "1",
                "Javaコース",
                LocalDateTime.of(2025, 1, 1, 3, 3),
                LocalDateTime.of(2025, 6, 30, 1, 1),
                null,
                null,
                "FINAL"
        );

        sut.registerStudentCourse(studentCourse);
        String courseId = studentCourse.getId();

        studentCourse.setCourseName("newCourse");
        sut.updateStudentCourse(studentCourse);

        StudentCourse updated = sut.findCourseById(courseId);
        assertThat(updated.getCourseName()).isEqualTo("newCourse");
        assertThat(updated.getCourseStartAt()).isEqualTo(LocalDateTime.of(2025, 1, 1, 3, 3));
        assertThat(updated.getCourseEndAt()).isEqualTo(LocalDateTime.of(2025, 6, 30, 1, 1));
    }
    @Test
    void 受講生コース情報が取得できること(){
        StudentCourse course = sut.findCourseById("1");

        System.out.println("取得した　applicationStatus: "+ course.getApplicationStatus());
        assertThat(course).isNotNull();
        assertThat(course.getApplicationStatus()).isEqualTo("PROVISIONAL");
    }

    @Test
    void 受講生コースのステータスが更新できること() {
        StudentCourse course = new StudentCourse(
                null,
                "1",
                "Javaコース",
                LocalDateTime.of(2025, 1, 1, 3, 3),
                LocalDateTime.of(2025, 6, 30, 1, 1),
                null,
                null,
                "PROVISIONAL"
        );
        sut.registerStudentCourse(course);

        assertThat(course.getId()).isNotNull();

        //ステータス更新
        course.setApplicationStatus("FINAL");
        sut.updateStudentCourse(course);

        //更新後の状態を取得して検証
        StudentCourse updatedCourse = sut.findCourseById(course.getId());
        assertThat(updatedCourse.getApplicationStatus()).isEqualTo("FINAL");
    }
}