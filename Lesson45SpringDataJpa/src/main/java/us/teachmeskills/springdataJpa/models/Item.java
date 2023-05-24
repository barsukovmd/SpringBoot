package us.teachmeskills.springdataJpa.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(schema = "test", name = "item")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "person_id", referencedColumnName = "id")//внешний ключ
    @ManyToOne
    private Person owner;

    @Column(name = "item_name")
    private String itemName;

    public Item(String itemName) {
        this.itemName = itemName;
    }
}
