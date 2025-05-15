package raisetech.student.management.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
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

  @Select("SELECT * FROM student_courses")
  List<StudentCourses> searchStudentCourses();

  @Insert("INSERT INTO `students` (name) VALUES (#{name})")
  void insertStudent(Student student);

  @Insert("INSERT INTO `student_courses` (course_name, student_id) VALUES (#{courseName}, #{student.id})")
  void insertStudentCourse(StudentCourses studentCourses);

}