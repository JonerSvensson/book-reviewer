package se.jonas.bookreviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class BookreviewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookreviewerApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "Book Reviewer is running! (V.1)";
	}

}
