package raisetech.student.management;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/student")
  public List<Student> getAllStudent() {
    return repository.getAllstudents();
  }

  @PostMapping("/student")
  public void registerStudent(@RequestParam String name, @RequestParam int age) {
    Student student = repository.searchByName(name);
    repository.registerStudent(name, age);
  }

  @PatchMapping("/student")
  public void updateStudentName(@RequestParam String name, @RequestParam int age) {
    repository.updateStudent(name, age);
  }

  @DeleteMapping("/student")
  public void deleteStudent(@RequestParam String name) {
    repository.deleteStudent(name);
  }

}