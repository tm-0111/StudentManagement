package raisetech.student.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;
import raisetech.student.management.domein.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.Arrays;
import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるcontrollerです。
 */
@RestController
public class StudentController {
    private StudentService service;


    @Autowired
    public StudentController(StudentService service) {
        this.service = service;

    }

    /**
     * 受講生一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     * @return　受講生一覧（全件）
     */
    //学生一覧
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }

    /**
     *  受講生検索です。
     *  IDに紐づく任意の受講生の情報を取得します。
     * @param id　受講生ID
     * @return　受講生
     */
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(@PathVariable String id) {
        return service.searchStudent(id);
    }


    //登録
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    @PostMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }
}


