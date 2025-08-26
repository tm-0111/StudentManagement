package raisetech.student.management.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @BeforeEach
    void setUp() {

        sut.registerStudent(new Student("1", "山田太郎", "ヤマダタロウ", "タロ",
                "taro@example.com", "東京", 25, "男性", null, false));
        sut.registerStudent(new Student("2", "鈴木一郎", "スズキイチロウ", "イチ",
                "ichiro@example.com", "大阪", 30, "男性", null, false));
        sut.registerStudent(new Student("3", "田中花子", "タナカハナコ", "ハナ",
                "hana@example.com", "北海道", 22, "女性", null, false));
        sut.registerStudent(new Student("4", "佐藤良子", "サトウリョウコ", "リョウ",
                "ryoko@example.com", "福岡", 28, "女性", null, false));
    }

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();
        assertThat(actual).hasSizeGreaterThanOrEqualTo(4);

        assertThat(actual).contains(
                new Student("1", "山田太郎", "ヤマダタロウ", "タロ", "taro@example.com", "東京", 25, "男性", null, false),
                new Student("3", "田中花子", "タナカハナコ", "ハナ", "hana@example.com", "北海道", 22, "女性", null, false),
                new Student("4", "佐藤良子", "サトウリョウコ", "リョウ", "ryoko@example.com", "福岡", 28, "女性", null, false)
        );
    }

    @Test
    void 受講生IDで検索が行えること() {
        Student actual = sut.searchStudent("1");
        assertThat(actual.getName()).isEqualTo("山田太郎");
        assertThat(actual.getEmail()).isEqualTo("taro@example.com");
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student(
                "5",
                "伊藤悠",
                "イトウハルカ",
                "ハル",
                "haruka@example.com",
                "愛知",
                35,
                "その他",
                null,
                false
        );
        sut.registerStudent(student);

        List<Student> actual = sut.search();
        assertThat(actual).hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    void 受講生コースの登録が行えること() {
        StudentCourse studentCourse = new StudentCourse(
                null,
                "1",
                "Javaコース",
                LocalDateTime.of(2023, 4, 1, 9, 0),
                LocalDateTime.of(2023, 7, 1, 15, 0),
                null,
                null,
                null
        );
        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse("1");
        assertThat(actual).isNotEmpty();
        assertThat(actual).anySatisfy(course -> assertThat(course.getCourseName()).isEqualTo("Javaコース"));
    }

    @Test
    void 受講生コースの更新が行えること() {
        StudentCourse studentCourse = new StudentCourse(
                null,
                "1",
                "testCourse",
                LocalDateTime.of(2025, 1, 1, 3, 3),
                LocalDateTime.of(2025, 6, 30, 1, 1),
                null,
                null,
                null
        );
        sut.registerStudentCourse(studentCourse);

        studentCourse.setCourseName("newCourse");
        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourse("1");
        assertThat(actual).anySatisfy(course -> assertThat(course.getCourseName()).isEqualTo("newCourse"));
    }

    @Test
    void 受講生コース情報が取得できること() {
        StudentCourse course = sut.findCourseById("1");
        assertThat(course).isNotNull();
        assertThat(course.getApplicationStatus()).isEqualTo("PROVISIONAL");
    }

    @Test
    void 受講生コースのステータスが更新できること() {
        StudentCourse course = new StudentCourse(
                null,
                "1",
                "Java",
                LocalDateTime.of(2025, 1, 1, 3, 3),
                LocalDateTime.of(2025, 6, 30, 1, 1),
                null,
                null,
                "PROVISIONAL"
        );
        sut.registerStudentCourse(course);

        // 登録された最新のコースを取得
        List<StudentCourse> courseList = sut.searchStudentCourse("1");
        StudentCourse courseToUpdate = courseList.get(courseList.size() - 1);

        courseToUpdate.setApplicationStatus("FINAL");
        sut.updateStudentCourse(courseToUpdate);

        StudentCourse updated = sut.findCourseById(courseToUpdate.getId());
        assertThat(updated.getApplicationStatus()).isEqualTo("FINAL");
    }
}
