package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class ProductUpdateDTO {
    private String title;
    private int price;

}
// END
