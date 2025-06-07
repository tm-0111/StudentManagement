package raisetech.student.management.controller.converter;

import org.springframework.stereotype.Component;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.domein.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 受講生と受講生コース情報を受講生詳細に変換するコンバーターです。
 */
@Component
public class StudentConverter {

    /**
     * 受講生に紐づく受講生コース情報をマッピングする。
     * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
     *
     * @param studentList　受講生一覧
     * @param studentCoursesList　受講生コース情報のリスト
     * @return　受講生詳細情報のリスト
     */
    public List<StudentDetail> convertStudentDetails(List<Student> studentList,
                                                     List<StudentCourse> studentCoursesList) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        studentList.forEach(student -> {
            StudentDetail studentDetail = new StudentDetail();
            studentDetail.setStudent(student);

            List<StudentCourse>convertStudentCoursesList = studentCoursesList.stream()
                            .filter(studentCourse -> student.getId().equals(studentCourse.getStudentId()))
                                    .collect(Collectors.toList());

            studentDetail.setStudentCourseList(convertStudentCoursesList);
            studentDetails.add(studentDetail);
        });
        return studentDetails;
    }
}
