package raisetech.student.management.date;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class StudentCourse {

    private String id;
    private String studentId;
    @NotBlank(message = "コース名は必須です")
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private LocalDateTime updateAt;

    @NotBlank(message = "申し込み状況は必須です")
    private String applicationStatus;
}

