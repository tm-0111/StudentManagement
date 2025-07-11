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

    @Transactional
    public void updateCourseStatus(String studentId, ApplicationStatus newStatus) {
        StudentCourse course = repository.findCourseById(studentId);

        if (course == null) throw new IllegalArgumentException("コースが見つかりません");

        if (!isValidTransition(course.getApplicationStatus(), newStatus)) {
            throw new IllegalArgumentException("不正な操作です:" + course.getApplicationStatus() + "→" + newStatus);
        }
            course.setApplicationStatus(newStatus);
            course.setUpdateAt(LocalDateTime.now());
            repository.updateStudentCourse(course);
    }

    private boolean isValidTransition(@NotNull(message = "申し込み状況は必須です") String applicationStatus, ApplicationStatus newStatus) {
    ApplicationStatus current;
    try {
        current = ApplicationStatus.valueOf(applicationStatus);
    } catch (IllegalArgumentException e){
        return false;
    }
    ApplicationStatus[] order = {
            ApplicationStatus.仮申込,
            ApplicationStatus.本申込,
            ApplicationStatus.受講中,
            ApplicationStatus.受講終了
    };
    int crrentIndex = -1;
    int newIndex = -1;

    for (int i = 0; i < order.length; i++){
        if (order[i] == current) crrentIndex = i;
        if (order[i] == newStatus) newIndex = i;
    }
    if (crrentIndex == -1 || newIndex == -1){
        return false;
    }
    return Math.abs(newIndex - crrentIndex) == 1 || newIndex == crrentIndex;
    }

    public void updateCourseStatus(String studentId, raisetech.student.management.controller.ApplicationStatus newStatus) {
    }
}