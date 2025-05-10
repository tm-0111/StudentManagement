package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> studentCourses) {

    List<StudentDetail> studentDetails = new ArrayList<>();

    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setId(student.getId());
      studentDetail.setName(student.getName());

      List<StudentCourses> coursesForStudent = studentCourses.stream()
          .filter(course -> Objects.equals(course.getStudent_id(), student.getId()))
          .collect(Collectors.toList());

      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
