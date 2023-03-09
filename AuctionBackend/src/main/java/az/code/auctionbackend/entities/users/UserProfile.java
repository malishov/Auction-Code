package az.code.auctionbackend.entities.users;

import az.code.auctionbackend.entities.auction.Bid;
import az.code.auctionbackend.entities.finance.Account;
import az.code.auctionbackend.entities.finance.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "profiles")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String address;

    private double rating;

    @Value("false")
    private boolean isBlocked;

    @OneToOne
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="roleId", nullable=false)
    @ToString.Exclude
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Bid> bidList;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Transaction> transactionList;
}