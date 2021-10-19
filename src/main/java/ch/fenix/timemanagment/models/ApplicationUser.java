package ch.fenix.timemanagment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Entry> entries = new ArrayList<>();
    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.REMOVE)
    private List<UserCategory> userCategories;
    @ManyToOne
    @JoinColumn
    private Role role;

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}