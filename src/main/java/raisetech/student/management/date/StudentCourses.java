package raisetech.student.management.date;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {

  private String id;
  private String student_id;
  private String courses_id;
  private LocalDate start_date;
  private LocalDate end_date;
  private String remarks;
  private boolean isDeleted;
  private String course_Name;
}
