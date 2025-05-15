package raisetech.student.management.date;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {


  private long id;
  private String name;
  private String kanaName;
  private String nickname;
  private String email;
  private String area;
  private int age;
  private String sex;
  private String remark;
  private boolean isDeleted;

}
