package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentCoursesRepository {

  @Select("SELECT * FROM student_courses")
  @Results({
      @Result(property = "student_id", column = "student_id"),
      @Result(property = "courses_id", column = "course_id"),
      @Result(property = "start_date", column = "start_date"),
      @Result(property = "end_date", column = "end_date"),
      @Result(property = "update_at", column = "update_at")
  })
  List<StudentCourses> getAll();

}
