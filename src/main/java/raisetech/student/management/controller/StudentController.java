package raisetech.student.management.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.Valid;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raisetech.student.management.domein.StudentDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして実行されるcontrollerです。
 */
@Validated
@RestController
public class StudentController {
    private StudentService service;


    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 受講生詳細の一覧検索です。
     * 全件検索を行うので、条件指定は行いません。
     * @return　受講生詳細一覧（全件）
     */
    @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
    @GetMapping("/studentList")
    public List<StudentDetail> getStudentList() {
        return service.searchStudentList();
    }
@GetMapping("/test")
public String  throwTestException() throws TestException {
        throw new TestException("testです。");
}
    /**
     *  受講生詳細検索です。IDに紐づく任意の受講生の情報を取得します。
     * @param id　受講生ID
     * @return　受講生
     */
    @Operation(summary = "受講生詳細取得", description = "指定したIDの受講生情報を取得します")
    @ApiResponse(responseCode = "200", description = "正常に取得されました。")
    @GetMapping("/student/{id}")
    public StudentDetail getStudent(
            @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
        return service.searchStudent(id);
    }

    /**
     * 受講生詳細の登録を行います。
     * @param studentDetail　受講生詳細
     * @return　実行結果を返します。
     */
    @Operation(summary = "受講生登録", description = "受講生を登録します。")
    @ApiResponse(responseCode = "200", description = "登録が成功しました。")
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
        StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
        return ResponseEntity.ok(responseStudentDetail);
    }

    /**
     * 受講生詳細の更新を行います。
     * キャンセルフラグの更新もここで行います。(論理削除)
     * @param studentDetail　受講生詳細
     * @return　実行結果
     */
    @Operation(summary = "受講生更新", description = "受講生情報を更新します。")
    @ApiResponse(responseCode = "200", description = "更新処理が成功しました。")
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
        service.updateStudent(studentDetail);
        return ResponseEntity.ok("更新処理が成功しました。");
    }
}


