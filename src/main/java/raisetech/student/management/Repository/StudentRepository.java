package raisetech.student.management.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.date.Student;
import raisetech.student.management.date.StudentCourses;

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

  @Select("SELECT id, name, kana_name, nickname, email, region AS area, age, gender AS sex, remarks AS remark, deleted FROM students WHERE deleted = false")
  List<Student> search();

  @Select("SELECT id, name, kana_name, nickname, email, region AS area, age, gender AS sex, remarks AS remark, deleted FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCoursesList();

  @Select("SELECT * FROM student_courses WHERE student_id = #{studentId}")
  List<StudentCourses> searchStudentCourses(String studentId);


  @Update(
      "UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email = #{email},"
          + " region = #{area}, age = #{age}, gender = #{sex}, remarks = #{remark}, deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);


  @Insert(
      "INSERT INTO students(name, nickname, kana_name, email, region, age, gender, remarks, deleted) "
          + "VALUES (#{name}, #{nickname}, #{kanaName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);


  @Insert("INSERT INTO student_courses (course_name, student_id, start_date, end_date, update_at)"
      + " VALUES (#{courseName}, #{studentId}, #{startDate}, #{endDate}, #{updateAt})")

  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourses studentCourses);


  @Delete("DELETE FROM student_courses WHERE student_id = #{studentId}")
  void deleteByStudentId(@Param("studentId") String studentId);

}
