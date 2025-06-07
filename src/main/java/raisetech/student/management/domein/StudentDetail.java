package raisetech.student.management.domein;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

    private Student student;
    private List<StudentCourses> studentCourses;
}
