package raisetech.student.management.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.date.Student;

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


  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findById(@Param("id") Long id);

  @Update(
      "UPDATE students SET name = #{name}, kana_name = #{kanaName}, nickname = #{nickname}, email = #{email},"
          + " region = #{area}, age = #{age}, gender = #{sex}, remarks = #{remark}, deleted = #{deleted} WHERE id = #{id}")
  void updateStudent(Student student);

  @Insert(
      "INSERT INTO students(name, nickname, kana_name, email, region, age, gender, remarks, deleted) "
          + "VALUES (#{name}, #{nickname}, #{kanaName}, #{email}, #{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);


}
