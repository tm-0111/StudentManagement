package raisetech.student.management.Repository;

import org.apache.ibatis.annotations.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

import java.util.List;

/**
 * 受講生テーブルと受講生情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います。
     *
     * @return 受講生一覧（全件）
     */
    @Select("SELECT * FROM students")
    List<Student> search();

    /**
     * 受講生検索を行います。
     * @param id　受講生ID
     * @return　受講生
     */
    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(String id);

    /**
     * 受講生のコース情報の全件検索を行います。
     *
     * @return　受講生のコース情報（全件）
     */

    @Select("SELECT * FROM students_courses")
    List<StudentCourses> searchStudentCoursesList();

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     * @param studentId　受講生ID
     * @return　受講生IDに紐づく受講生コース情報
     */
    @Select("SELECT * FROM students_courses WHERE id = #{id}")
    List<StudentCourses> searchStudentCourses(String studentId);

    @Insert(
            "INSERT INTO students(name, nickname, kana_name, email, area, age, sex, remark, is_deleted) "
                    + "VALUES (#{name}, #{nickname}, #{kanaName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);


    @Insert("INSERT INTO students_courses ( student_id, course_name, course_start_at, course_end_at)"
            + " VALUES (#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourse(StudentCourses studentCourses);


    @Update(
            "UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email = #{email},"
                    + " area = #{area}, age = #{age}, sex = #{sex}, remark = #{remark}, is_deleted = #{deleted} WHERE id = #{id}")
    void updateStudent(Student student);

    @Update("UPDATE students_courses SET courses_name = #{courseName} WHERE id = #{id}")
    void updateStudentCourses(StudentCourses studentCourses);

   //　@Delete("DELETE FROM students courses WHERE student_id = #{studentId}")
   //void deleteByStudentId(@Param("student_id") String studentId);

}
