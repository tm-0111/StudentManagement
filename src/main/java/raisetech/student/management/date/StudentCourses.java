package raisetech.student.management.date;

import java.time.LocalDate;
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
}
