package az.code.auctionbackend.services.interfaces;

import az.code.auctionbackend.entities.Account;
import az.code.auctionbackend.entities.Transaction;
import az.code.auctionbackend.entities.UserProfile;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    double getBalance(long accountId);

    Account getAccountDetails(long accountId);

    void topUpBalance(long accountId, double amount);
    List<Account> getAllAccounts();

    int purchase(long senderId, long receiverId, double amount);

}