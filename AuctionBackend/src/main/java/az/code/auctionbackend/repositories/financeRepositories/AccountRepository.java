package az.code.auctionbackend.repositories.financeRepositories;

import az.code.auctionbackend.entities.finance.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}