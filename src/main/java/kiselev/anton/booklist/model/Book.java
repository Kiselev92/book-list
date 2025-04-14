package kiselev.anton.booklist.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private Long id;
    private String vendorcode;
    private String title;
    private int year;
    private String brand;
    private int stock;
    private int price;
}