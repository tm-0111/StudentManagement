package raisetech.student.management;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {

  private Long id;
  private Long student_id;
  private Long courses_id;
  private LocalDate start_date;
  private LocalDate end_date;
  private LocalDateTime update_at;

}
