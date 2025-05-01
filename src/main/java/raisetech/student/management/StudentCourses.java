package raisetech.student.management;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {

  private Long id;
  private Long student_Id;
  private Long courses_Id;
  private LocalDate start_Date;
  private LocalDate end_Date;
  private LocalDateTime update_At;

}
