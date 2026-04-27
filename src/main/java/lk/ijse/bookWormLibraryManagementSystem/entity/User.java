package lk.ijse.bookWormLibraryManagementSystem.entity;

import lk.ijse.bookWormLibraryManagementSystem.embedded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Timestamp dateTimeUpdate;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user"
    )
    private List<Transaction> transactions = new ArrayList<>();
    // ── Builder ──
    public static class Builder {
        private final User user = new User();

        public User.Builder id(int id)               { user.setId(id);           return this; }
        public User.Builder name(String name)         { user.setName(name);       return this; }
        public User.Builder email(String email)       { user.setEmail(email);     return this; }
        public User.Builder username(String username) { user.setUsername(username); return this; }
        public User.Builder password(String password) { user.setPassword(password); return this; }
        public Builder admin(Admin admin)             { user.setAdmin(admin); return this; }
        public User build() { return user; }
    }

}
