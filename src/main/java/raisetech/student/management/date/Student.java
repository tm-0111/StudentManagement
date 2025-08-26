package raisetech.student.management.date;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Student {

   // @Pattern(regexp = "\\d+", message = "数字のみ入力するようにしてください。")
    private String id;

    @NotBlank(message = "名前は必須です")
    private String name;

    @Size(max = 30, message = "ふりがなは３０文字以内で入力してください")
    private String kanaName;

    @Size(max = 30, message = "ニックネームは３０文字以内で入力してください")
    private String nickname;

    @Email(message = "正しいメールアドレスを入力してください")
    private String email;

    @Size(max = 20, message = "地域名は20文字以内で入力してください")
    private String area;

    @Min(value = 0, message = "年齢は0以上を入力してください")
    private int age;

    @Pattern(regexp = "^(男性|女性|その他|回答しない)$",message = "性別はいずれかを入力してください")
    private String sex;

    @Size(max = 255, message = "備考は255文字以内で入力してください")
    private String remark;

    private boolean deleted;
}
