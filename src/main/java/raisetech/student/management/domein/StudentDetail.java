package raisetech.student.management.domein;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {
@Valid
@NotNull
    private Student student;
    private List<@NotNull StudentCourse> studentCourseList;
}

