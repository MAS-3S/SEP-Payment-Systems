package issuer.bank.issuerbankbackend.repository;

import issuer.bank.issuerbankbackend.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
