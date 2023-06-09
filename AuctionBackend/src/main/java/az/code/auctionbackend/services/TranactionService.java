package az.code.auctionbackend.services;

import az.code.auctionbackend.entities.Account;
import az.code.auctionbackend.entities.Transaction;
import az.code.auctionbackend.entities.UserProfile;
import az.code.auctionbackend.repositories.financeRepositories.AccountRepo;
import az.code.auctionbackend.repositories.financeRepositories.TransactionRepository;
import az.code.auctionbackend.services.interfaces.AccountService;
import az.code.auctionbackend.services.interfaces.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Access;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class TranactionService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepo accountRepo;


    public Transaction createTransaction(double amount, Account sender, Account receiver, int status){

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .account(receiver)
                .transactionTime(LocalDateTime.now())
                .senderUsername(sender.getUser().getUsername())
                .status(status)
                .build();

        log.info(transactionRepository.save(transaction));

        return transaction;
    }

    public Transaction saveTransaction(Transaction transaction){
//        return transactionRepository.save(transaction);
        return entityManager.merge(transaction);
    }

    @Transactional
    public Account saveAccount(Account acc){
        return entityManager.merge(acc);
    }

    public List<Transaction> getUsersInvolvedTransactions(String username){
        return accountRepo.getUserInvolvedTransactions(username);
    }

}
