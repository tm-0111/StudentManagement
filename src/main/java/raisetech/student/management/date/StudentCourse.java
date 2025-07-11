package raisetech.student.management.date;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import raisetech.student.management.ApplicationStatus;

import java.time.LocalDateTime;

@Getter
@Setter

public class StudentCourse {

    private String id;
    private String studentId;
    @NotNull(message = "コース名は必須です")
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private LocalDateTime updateAt;
    @NotNull(message = "申し込み状況は必須です")
    private String applicationStatus;

    public void setApplicationStatus(ApplicationStatus newStatus) {
    }
}

