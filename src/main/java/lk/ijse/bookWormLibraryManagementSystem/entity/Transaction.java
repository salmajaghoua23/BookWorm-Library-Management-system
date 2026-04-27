package lk.ijse.bookWormLibraryManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "book_qty", nullable = false)
    private int bookQty;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @CreationTimestamp
    @Column(name = "date_and_time")
    private Timestamp dateAndTime;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Timestamp dateTimeUpdate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "transaction"
    )
    private List<TransactionDetail> transactionDetails = new ArrayList<>();
    // ── Builder ──
    public static class Builder {
        private final Transaction transaction = new Transaction();

        public Builder id(int id)                          { transaction.setId(id);                      return this; }
        public Builder transactionType(String type)        { transaction.setTransactionType(type);       return this; }
        public Builder bookQty(int bookQty)                { transaction.setBookQty(bookQty);            return this; }
        public Builder dueDate(String dueDate)             { transaction.setDueDate(dueDate);            return this; }
        public Builder dateAndTime(Timestamp dateAndTime)  { transaction.setDateAndTime(dateAndTime);    return this; }
        public Builder user(User user)                     { transaction.setUser(user);                  return this; }

        public Transaction build() { return transaction; }
    }

}
