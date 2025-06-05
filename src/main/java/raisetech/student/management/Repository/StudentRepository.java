package raisetech.student.management.Repository;

import org.apache.ibatis.annotations.*;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

import java.util.List;

/**
 * Student information repository
 * <p>
 * A class that handles full record retrieval, conditional searches, and course information
 * queries.
 */
@Mapper
public interface StudentRepository {

    /**
     * find all
     *
     * @return A list of all retrieved student information
     */

    @Select("SELECT id, name, kana_name, nickname, email, area, age, sex, remark, is_deleted FROM students WHERE is_deleted = false")
    List<Student> search();

    @Select("SELECT id, name, kana_name, nickname, email, area, age, sex, remark, is_deleted FROM students WHERE id = #{id}")
    Student searchStudent(String id);

    @Select("SELECT * FROM students courses")
    List<StudentCourses> searchStudentCoursesList();

    @Select("SELECT * FROM students courses WHERE id = #{id}")
    List<StudentCourses> searchStudentCourses(String studentId);


    @Update(
            "UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email = #{email},"
                    + " area = #{area}, age = #{age}, sex = #{sex}, remarks = #{remark}, is_deleted = #{deleted} WHERE id = #{id}")
    void updateStudent(Student student);


    @Insert(
            "INSERT INTO students(name, nickname, kana_name, email, region, age, gender, remarks, is_deleted) "
                    + "VALUES (#{name}, #{nickname}, #{kanaName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);


    @Insert("INSERT INTO students courses (course_name, id, start_date, end_date, update_at)"
            + " VALUES (#{courseName}, #{id}, #{startDate}, #{endDate}, #{updateAt})")

    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudentCourse(StudentCourses studentCourses);


   // @Delete("DELETE FROM students courses WHERE id = #{id}")
  //  void deleteByStudentId(@Param("id") String studentId);

}
