package raisetech.student.management.date;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {


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
    private int age;
    @Pattern(regexp = "^(男性｜女性｜その他｜回答しない)?$",message = "性別はいずれかを入力してください")
    private String sex;
    @Size(max = 255, message = "備考は255文字以内で入力してください")
    private String remark;
    private boolean deleted;
}
