package ch.fenix.timemanagment.models;

import ch.fenix.timemanagment.data.Interval;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String name;
    @Setter
    private float min; //in hours
    @Setter
    @Transient
    private Interval interval;
    private String intervalValue;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Entry> entries = new ArrayList<>();
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<UserCategory> userCategories = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    @PreRemove
    public void removeRelations() {
        entries.forEach(entry -> entry.setCategory(null));
    }

    @PrePersist
    public void convertEnum() {
        intervalValue = interval==null?null:interval.name();
    }

    @PostLoad
    public void loadEnum() {
        interval = intervalValue==null?null:Interval.valueOf(intervalValue);
    }
}
