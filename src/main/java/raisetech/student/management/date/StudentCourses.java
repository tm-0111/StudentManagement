package raisetech.student.management.date;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {


  private long id;
  private long student_id;
  private LocalDateTime start_date;
  private LocalDateTime end_date;
  private String courseName;
  private LocalDateTime update;

}
