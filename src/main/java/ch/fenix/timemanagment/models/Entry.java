package ch.fenix.timemanagment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Entry {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime checkIn;
    @Column(nullable = false)
    private LocalDateTime checkOut;
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private ApplicationUser user;
    @ManyToOne
    @JoinColumn
    private Category category;

    public Entry(LocalDateTime checkIn, LocalDateTime checkOut, ApplicationUser user, Category category) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.category = category;
        this.user = user;
    }
}
