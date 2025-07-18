package raisetech.student.management.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.student.management.ApplicationStatus;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.domein.StudentDetail;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

    private StudentRepository repository;
    private StudentConverter converter;

    @Autowired
    public StudentService(StudentRepository repository, StudentConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     *
     * @return　受講生詳細一覧（全件）
     */

    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = repository.search();
        List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
        return converter.convertStudentDetails(studentList, studentCourseList);
    }

    /**
     * 受講生詳細検索です。
     * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     *
     * @param id 　受講生ID
     * @return　受講生
     */

    public StudentDetail searchStudent(String id) {
        Student student = repository.searchStudent(id);
        List<StudentCourse> studentCourse = repository.searchStudentCourse(student.getId());
        return new StudentDetail(student, studentCourse);
    }

    /**
     * 受講生詳細の登録を行います。
     * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
     *
     * @param studentDetail 　受講生詳細
     * @return　登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        repository.registerStudent(student);
        studentDetail.getStudentCourseList().forEach(studentCourse -> {
            initStudentsCourse(studentCourse, student);
            repository.registerStudentCourse(studentCourse);
        });
        return studentDetail;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     *
     * @param studentCourse 　受講生コース情報
     * @param student       　受講生
     */
    public void initStudentsCourse(StudentCourse studentCourse, Student student) {
        LocalDateTime now = LocalDateTime.now();

        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseStartAt(now);
        studentCourse.setCourseEndAt(now.plusYears(1));
    }

    /**
     * 受講生詳細の更新を行います。
     * 受講生と受講生コース情報をそれぞれ更新します。
     *
     * @param studentDetail 　受講生詳細
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        //学生情報を更新
        repository.updateStudent(studentDetail.getStudent());
        studentDetail.getStudentCourseList()
                .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
    }

    /**
     * 受講生詳細の更新を行います。
     * 受講生と受講生コース情報をそれぞれ更新します。
     *
     * @param studentDetail 　受講生詳細
     */
    @Transactional
    public void updatedStudent(StudentDetail studentDetail) {
        //学生情報を更新
        repository.updateStudent(studentDetail.getStudent());
        studentDetail.getStudentCourseList()
                .forEach(studentCourse -> repository.updateStudentCourse(studentCourse));
    }

    /**
     * 受講生コースのステータスを更新する
     *
     * @param courseId 対象受講生のID
     * @param newStatus 移行先のステータス
     * @throws IllegalArgumentException 無効な移行やコース未存在の場合
     */
    @Transactional
    public void updateCourseStatus(String courseId, ApplicationStatus newStatus) {
        StudentCourse course = repository.findCourseById(courseId);

        if (course == null) {
            throw new IllegalArgumentException("コースが見つかりません");
        }

        ApplicationStatus currentStatus = ApplicationStatus.valueOf(course.getApplicationStatus());

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalArgumentException("不正な操作です:" + currentStatus + "→" + newStatus);
        }
        course.setApplicationStatus(String.valueOf(newStatus));
        course.setUpdatedAt(LocalDateTime.now());
        repository.updateStudentCourse(course);

    }
    /**
     * ステータス移行が有効かを判定する
     * 　1段階のみ前後の移行を許可（例: 仮申込⇄本申込）
     * 　COMPLETED（受講終了）からの移行は禁止
     *
     * @param current 現在のステータス
     * @param next    移行先のステータス
     * @return 有効な移行であれば true、それ以外は false
     */
    private boolean isValidTransition (ApplicationStatus current, ApplicationStatus next){
        if (current == ApplicationStatus.COMPLETED) {
            return false;
        }
        int diff = Math.abs(current.ordinal() - next.ordinal());
        return diff == 1;
    }
}