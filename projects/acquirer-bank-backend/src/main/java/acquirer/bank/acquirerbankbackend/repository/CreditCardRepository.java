package acquirer.bank.acquirerbankbackend.repository;

import acquirer.bank.acquirerbankbackend.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
    CreditCard findByMerchantIdAndMerchantPassword(String merchantId, String merchantPassword);
}
