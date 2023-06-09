package az.code.auctionbackend.services;

import az.code.auctionbackend.entities.Account;
import az.code.auctionbackend.entities.Transaction;
import az.code.auctionbackend.entities.UserProfile;
import az.code.auctionbackend.repositories.UserRepo;
import az.code.auctionbackend.repositories.financeRepositories.AccountRepo;
import az.code.auctionbackend.repositories.financeRepositories.AccountRepository;
import az.code.auctionbackend.repositories.financeRepositories.TransactionRepository;
import az.code.auctionbackend.repositories.redisRepositories.RedisRepository;
import az.code.auctionbackend.repositories.usersRepositories.UserRepository;
import az.code.auctionbackend.services.interfaces.AccountService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accRepo;
    private final TranactionService tranactionService;
    private final RedisRepository redisRepository;

//    @PostConstruct
    public void AccountTest() {

        //Error
//        transactionRepository.save(Transaction.builder()
//                .account(getAccountDetails(1))
//                .amount(5)
//                .sender(getAccountDetails(2).getUser()).build());

//        System.out.println(getAccountDetails(1));
//        System.out.println(getAccountDetails(3));

//        topUpBalance(1, -10);
//        topUpBalance(2, 100);
//        List<Transaction> transactions = userRepository.findById(2L).get().getAccount().getTransactions();
//
//
//        System.out.println(purchase(userRepository.findById(2L).get(), userRepository.findById(3L).get(), 1));
//        System.out.println(getAccountDetails(1));
//        System.out.println(getAccountDetails(2));
    }

    @Override
    public List<Account> getAllAccounts() {
        return accRepo.getAllAccs();
    }

    @Override
    public Account getAccountDetails(long accountId) {

        return accRepo.searchAccountById(accountId).orElse(null);
    }


    @Override
    public double getBalance(long accountId) {

        return accRepo.searchAccountById(accountId)
                .map(Account::getBalance)
                .orElse(0.0);
    }

    @Override
    public void topUpBalance(long accountId, double amount) {

        log.info("Top up account " + accountId);
        Account account = getAccountDetails(accountId);
        // TODO validation
        if (account == null || !account.isActive()) return;

        double balance = account.getBalance();
        account.setBalance(balance + amount);

        log.info("saving account " + account);
        log.info("saved" + accRepo.saveAccount(account));
    }


    @Override
    public int purchase(long senderId, long receiverId, double amount){
        /**
         Returns 1 if it's ok; 0 if insufficient funds; -1 if error
         */

        log.info("Purchase");

        Account senderAccount = accRepo.searchAccountById(senderId).get();
        Account receiverAccount = accRepo.searchAccountById(receiverId).get();


        if(!senderAccount.isActive() || !receiverAccount.isActive()) {
            return -1;
        } else if (senderAccount.getBalance() < amount) {

            tranactionService.createTransaction(amount, receiverAccount, senderAccount, -1);
            return 0;
            // status - 406
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        } else {

        topUpBalance(senderId, amount * -1);
        topUpBalance(receiverId, amount);

        tranactionService.createTransaction(amount, receiverAccount, senderAccount, 1);

        return 1;
        }
    }





}