package raisetech.student.management.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourses> searchStudentCourses();

  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<Student> searchStudentsInTheir30s();

  @Select("SELECT * FROM students_courses WHERE course name = 'Java' ")
  List<StudentCourses> searchJavaCourses();

}
