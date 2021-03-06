package acquirer.bank.acquirerbankbackend.repository;

import acquirer.bank.acquirerbankbackend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Transaction getById(String id);
}
