package issuer.bank.issuerbankbackend;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class IssuerBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssuerBankBackendApplication.class, args);
	}

}
