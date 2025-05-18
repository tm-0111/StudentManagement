package raisetech.student.management.date;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentCourses {

  private String id;
  private String studentId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String courseName;
  private LocalDateTime updateAt;
}
