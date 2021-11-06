package ch.fenix.timemanagment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private float balance = 0;
    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private ApplicationUser applicationUser;
    @ManyToOne
    @JoinColumn
    private Category category;

    public UserCategory(ApplicationUser applicationUser, Category category) {
        this.applicationUser = applicationUser;
        this.category = category;
    }
}
