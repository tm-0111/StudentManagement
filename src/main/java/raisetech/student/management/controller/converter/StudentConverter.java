package raisetech.student.management.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 受講生と受講生コース情報を受講生詳細に変換するコンバーターです。
 */
@Component
public class StudentConverter {

    /**
     * 受講生に紐づく受講生コース情報をマッピングする。
     * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
     *
     * @param students　受講生一覧
     * @param courses　受講生コース情報のリスト
     * @return　受講生詳細情報のリスト
     */
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
