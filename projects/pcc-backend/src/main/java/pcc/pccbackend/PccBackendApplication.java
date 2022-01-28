package pcc.pccbackend;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class PccBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PccBackendApplication.class, args);
	}

}
