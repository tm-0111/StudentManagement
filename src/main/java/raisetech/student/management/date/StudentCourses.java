package raisetech.student.management.date;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {


  private long id;
  private long studentId;
  private LocalDateTime start_Date;
  private LocalDateTime end_Date;
  private String courseName;
  private LocalDateTime update;

}
