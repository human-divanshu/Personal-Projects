package in.dsingh.domaindata.domaindetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DomaindetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomaindetailsApplication.class, args);
	}
}
