package raisetech.student.management.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.date.Student;
import raisetech.student.management.domein.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import raisetech.student.management.ApplicationStatus;


@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private String status;

    @Test
    void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
        when(service.searchStudentList()).thenReturn(List.of());

        mockMvc.perform(get("/studentList"))
                .andExpect(status().isOk());
        verify(service, times(1)).searchStudentList();
    }

    @Test
    void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
        Student student = new Student();
        student.setId("2");
        student.setName("佐藤花子");
        student.setKanaName("サトウハナコ");
        student.setNickname("はなこ");
        student.setArea("大阪");
        student.setEmail("hanako@example.com");
        student.setAge(20);
        student.setSex("女性");
        student.setRemark("テスト用データです");
        student.setDeleted(false);

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    void 受講生詳細の受講生でIDに数字以外を用いた時にチェックに掛かること() {
        Student student = new Student();
        student.setId("テストです");
        student.setName("佐藤花子");
        student.setKanaName("サトウハナコ");
        student.setNickname("はなこ");
        student.setArea("大阪");
        student.setEmail("hanako@example.com");
        student.setAge(20);
        student.setSex("女性");

        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations).extracting("message")
                .containsOnly("数字のみ入力するようにしてください。");
    }

    @Test
    void 受講生IDで検索し正常に返ること() throws Exception {
        String id = "999";
        Student student = new Student();
        student.setId(id);
        student.setName("佐藤花子");

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourseList(List.of());

        when(service.searchStudent(id)).thenReturn(detail);

        mockMvc.perform(get("/student/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void 受講生詳細の登録が実行できて空で返ってくること() throws Exception {
        //リクエストデータは適切に構築して入力チェックの検証も兼ねてます。
        //本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない。
        mockMvc.perform(post("/registerStudent")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"student\": {"
                                + "\"name\": \"山田太郎\","
                                + "\"kanaName\": \"ヤマダタロウ\","
                                + "\"nickname\": \"タロ\","
                                + "\"email\": \"taro@example.com\","
                                + "\"area\": \"東京\","
                                + "\"age\": 25,"
                                + "\"sex\": \"男性\","
                                + "\"remark\": \"\""
                                + "},"
                                + "\"studentCourseList\": ["
                                + "{ \"courseName\": \"JAVAコース\" }"
                                + "]"
                                + "}")
                )
                .andExpect(status().isOk());

        verify(service, times(1)).registerStudent(any());
    }
    @Test
    void 不正なデータで受講生詳細を登録しバリデーションエラーになること() throws Exception{

        mockMvc.perform(post("/registerStudent")
                        //名前が空の場合
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                        + "\"student\": {"
                        + "\"name\": \"\","//←名前が空
                        + "\"kanaName\": \"ヤマダタロウ\","
                        + "\"nickname\": \"タロ\","
                        + "\"email\": \"taro@example.com\","
                        + "\"area\": \"東京\","
                        + "\"age\": 25,"
                        + "\"sex\": \"男性\","
                        + "\"remark\": \"\""
                        + "},"
                        + "\"studentCourseList\": ["
                        + "{ \"courseName\": \"JAVAコース\" }"
                        + "]"
                        + "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 受講生詳細の更新ができて空で返ってくること() throws Exception {
        mockMvc.perform(put("/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{"
                                        + "\"student\": {"
                                        + "\"name\": \"山田太郎\","
                                        + "\"kanaName\": \"ヤマダタロウ\","
                                        + "\"nickname\": \"タロ\","
                                        + "\"email\": \"taro@example.com\","
                                        + "\"area\": \"東京\","
                                        + "\"age\": 25,"
                                        + "\"sex\": \"男性\","
                                        + "\"remark\": \"\""
                                        + "},"
                                        + "\"studentCourseList\": ["
                                        + "{"
                                        + "\"id\": \"1\","
                                        + "\"studentId\": \"10\","
                                        + "\"courseName\": \"JAVAコース\","
                                        + "\"courseStartAt\": \"2024-01-27T10:50:39.833614\","
                                        + "\"courseEndAt\": \"2025-04-27T10:50:39.833614\""
                                        + "}"
                                        + "]"
                                        + "}"
                        ))
                .andExpect(status().isOk())
                .andExpect(content().string("更新処理が成功しました。"));

        verify(service, times(1)).updateStudent(any());
    }

    @Test
    void 存在しないAPIにアクセスすると400エラーが返る() throws Exception {
        mockMvc.perform(get("/exception"))

                .andExpect(status().isBadRequest())
                .andExpect(content().string("このAPIは現在使用できません。古いURLとなってます。"));
    }
    @Test
    void ステータス更新に成功すること()throws Exception{
        String courseId = "1";
        String newStatus = "FINAL";

        doNothing().when(service).updateCourseStatus(courseId, ApplicationStatus.valueOf(newStatus));

        mockMvc.perform(put("/studentCourse/{courseId}/status",courseId)
                .param("newStatus",newStatus))
                .andExpect(status().isOk())
                .andExpect(content().string("申込情報を更新しました"));

        verify(service).updateCourseStatus(courseId, ApplicationStatus.valueOf(newStatus));
    }
    @Test
    void ステータス更新で不正な操作なら400エラーを返すこと()throws Exception{
        String courseId = "1";
        String newStatus = "FINAL";
        ApplicationStatus newStatusEnum = ApplicationStatus.valueOf(newStatus);

        doThrow(new IllegalArgumentException("不正なステータスです"))
                .when(service).updateCourseStatus(courseId, ApplicationStatus.valueOf(newStatus));

        mockMvc.perform(put("/studentCourse/{courseId}/status", courseId)
                .param("newStatus",newStatus))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("不正なステータスです"));
    }
}
