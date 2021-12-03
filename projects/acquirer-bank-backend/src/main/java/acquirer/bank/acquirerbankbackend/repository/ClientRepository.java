package acquirer.bank.acquirerbankbackend.repository;

import acquirer.bank.acquirerbankbackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
