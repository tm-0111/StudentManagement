package raisetech.student.management.converter;

import org.junit.jupiter.api.Test;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.domein.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentConverterTest {

    private final StudentConverter converter = new StudentConverter();

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

        List<StudentDetail> result = converter.convertStudentDetails(
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

        List<StudentDetail> result = converter.convertStudentDetails(
                List.of(student),
                List.of()
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudent().getId()).isEqualTo("999");
        assertThat(result.get(0).getStudentCourseList()).isEmpty();
    }
}

