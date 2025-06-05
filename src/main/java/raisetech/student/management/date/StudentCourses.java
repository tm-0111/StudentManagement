package raisetech.student.management.date;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
