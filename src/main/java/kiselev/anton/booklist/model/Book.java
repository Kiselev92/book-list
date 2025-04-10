package kiselev.anton.booklist.model;

import lombok.*;

@With
@Data
@Value
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Book {

    Long id;

    String vendorCode;

    String title;

    int year;

    String brand;

    int stock;

    int price;
}
