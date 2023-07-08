package by.teachmeskills.springbootexample.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private int id;
    @CsvBindByName
    @NotNull
    private String name;
    @CsvBindByName
    private int rating;
    private LocalDate localDate;
    private List<ProductDto> products;
}
