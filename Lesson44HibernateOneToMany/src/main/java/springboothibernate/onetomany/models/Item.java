package springboothibernate.onetomany.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(schema = "test", name = "item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @ManyToOne
    private Person owner;

    @Column(name = "item_name")
    private String itemName;

    public Item(Person owner, String itemName) {
        this.owner = owner;
        this.itemName = itemName;
    }
}
