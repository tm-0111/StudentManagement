package raisetech.student.management.Repository;


import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.date.StudentCourses;

public interface StudentCoursesRepository {


  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  @Select("SELECT * FROM student_courses WHERE student_id = #{student_Id}")
  List<StudentCourses> findByStudentId(@Param("studentId") Long studentId);

  @Insert("INSERT INTO student_courses (course_name, student_id, start_date, end_date, update_at)"
      + " VALUES (#{courseName}, #{student_Id}, #{start_date}, #{end_date}, #{update})")

  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudentCourse(StudentCourses studentCourses);

  @Delete("DELETE FROM student_courses WHERE student_id = #{student_Id}")
  void deleteByStudentId(@Param("studentId") Long studentId);
}
