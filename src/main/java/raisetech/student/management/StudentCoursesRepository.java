package raisetech.student.management;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentCoursesRepository {

  List<StudentCourses> searchStudentCourses();

}
