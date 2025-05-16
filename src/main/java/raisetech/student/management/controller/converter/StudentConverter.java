package raisetech.student.management.controller.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(List<Student> students,
      List<StudentCourses> courses) {

    Map<String, List<StudentCourses>> courseMap = new HashMap<>();
    for (StudentCourses course : courses) {
      if (course.getStudentId() != null) {
        courseMap.computeIfAbsent(course.getStudentId(), k -> new ArrayList<>()).add(course);
      }
    }

    List<StudentDetail> studentDetails = new ArrayList<>();
    students.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);
      studentDetail.setStudentCourses(courseMap.getOrDefault(student.getId(), new ArrayList<>()));

      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
