package raisetech.student.management.date;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class StudentCourse {

    private String id;
    private String studentId;
    @NotNull(message = "コース名は必須です")
    private String courseName;
    private LocalDateTime courseStartAt;
    private LocalDateTime courseEndAt;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    //@NotNull(message = "申し込み状況は必須です")
    private String applicationStatus;

}