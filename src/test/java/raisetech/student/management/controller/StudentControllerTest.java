package raisetech.student.management.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.date.Student;
import raisetech.student.management.domein.StudentDetail;
import raisetech.student.management.service.StudentService;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private StudentService service;
        private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        @Test
        void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception{
            when(service.searchStudentList()).thenReturn(List.of());

         mockMvc.perform(get("/studentList"))
                 .andExpect(status().isOk());
                // .andExpect(content().json("[\"student\":null,\"studentCourseList\":null]"));

         verify(service, times(1)).searchStudentList();
    }
    @Test
    void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと(){
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
    void 受講生詳細の受講生でIDに数字以外を用いた時にチェックに掛かること(){
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
    void 受講生IDで検索し正常に返ること() throws Exception{
        String id = "999";
        Student student = new Student();
        student.setId(id);
        student.setName("佐藤花子");

        StudentDetail detail = new StudentDetail();
        detail.setStudent(student);
        detail.setStudentCourseList(List.of());

        when(service.searchStudent(id)).thenReturn(detail);

        mockMvc.perform(get("/student/{id}",id))
                .andExpect(status().isOk());
    }
    @Test
    void 使用できないAPIにアクセスすると400とエラーメッセージが返る() throws Exception{
            mockMvc.perform(get("/exception"))

                .andExpect(status().isBadRequest())
                .andExpect(content().string("このAPIは現在使用できません。古いURLとなってます。"));
    }
}