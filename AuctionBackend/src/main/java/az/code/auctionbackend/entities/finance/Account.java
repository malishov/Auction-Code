package az.code.auctionbackend.entities.finance;

import az.code.auctionbackend.entities.users.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.List;


@Entity
@Data
@Builder
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserProfile user;

    private double balance;

    private boolean isActive;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}
