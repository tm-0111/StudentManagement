package raisetech.student.management.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.Repository.StudentRepository;
import raisetech.student.management.controller.converter.StudentConverter;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourse;
import raisetech.student.management.domein.StudentDetail;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentConverter converter;

    private StudentService sut;

    @BeforeEach
    void before(){
        sut =new StudentService(repository,converter);
    }

    @Test
    void  受講生一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること(){
        //事前準備
        StudentService sut = new StudentService(repository,converter);
        List<Student> studentList = new ArrayList<>();
        List<StudentCourse> studentCourseList = new ArrayList<>();
        when(repository.search()).thenReturn(studentList);
        when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

        List<StudentDetail> actual = sut.searchStudentList();

        verify(repository, times(1)).search();
        verify(repository, times(1)).searchStudentCourseList();
        verify(converter, times(1)).convertStudentDetails(studentList,studentCourseList);
    }
}