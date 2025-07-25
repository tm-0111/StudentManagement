package raisetech.student.management.Repository;
import org.apache.ibatis.annotations.*;
import raisetech.student.management.ApplicationStatus;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import java.util.List;
/**
 * 受講生テーブルと受講生情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {
    /**
     * @return 受講生一覧（全件）
     */
    List<Student> search();

    /**
     * 受講生検索を行います。
     * @param id　受講生ID
     * @return　受講生
     */
    Student searchStudent(String id);

    /**
     * 受講生のコース情報の全件検索を行います。
     *
     * @return　受講生のコース情報（全件）
     */
    List<StudentCourse> searchStudentCourseList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     * @param studentId　受講生ID
     * @return 受講生IDに紐づく受講生コース情報
     */
    List<StudentCourse> searchStudentCourse(String studentId);

    /**
     * コースIDで受講生コースを取得する。
     * @param courseId　コースID
     */
    StudentCourse findCourseById(String courseId);

    /**
     *受講生を新規登録します。IDに関しては自動採番を行う。
     * @param student　受講生
     */
    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
     * @param studentCourse　受講生コース情報
     */
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生を更新します。
     * @param student　受講生
     */
       void updateStudent(Student student);

    /**
     * 受講生コース情報のコース名を更新します。
     * @param studentCourse　受講生コース情報
     */
    void updateStudentCourse(StudentCourse studentCourse);


}
