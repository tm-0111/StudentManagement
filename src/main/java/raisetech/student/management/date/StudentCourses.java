package raisetech.student.management.date;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {

  private Student student;
  private long id;
  private long student_Id;
  private long courses_Id;
  private LocalDate start_date;
  private LocalDate end_date;
  private String remarks;
  private boolean isDeleted;
  private String courseName;

}
