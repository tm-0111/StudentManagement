package raisetech.student.management;


import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

	private Map<String, String> student = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/student")
	public Map<String,String> getStudent(){
		return  student;
	}

  @PostMapping("/studentInfo")
	public String getStudentInfo(@RequestParam String name,@RequestParam  String age){
		student.put(name, age);
		return name + age + "sai";
	}
}
