package by.teachmeskills.springbootexample.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;
    @Column(name = "image_path")
    private String imagePath;
    private int rating;

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    //It's better to exclude all lazy initialized fields to prevent recursive call of toString method
    @ToString.Exclude
    private List<Product> productList;

}
