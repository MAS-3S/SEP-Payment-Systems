package pcc.pccbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pcc.pccbackend.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
