package raisetech.student.management.date;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter

public class StudentCourse {

    private String id;
    private String studentId;
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private LocalDateTime updateAt;
}
