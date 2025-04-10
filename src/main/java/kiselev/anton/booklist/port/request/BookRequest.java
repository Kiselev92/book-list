package kiselev.anton.booklist.port.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookRequest {
    private Long id;

    private String vendorCode;

    private String title;

    private int year;

    private String brand;

    private int stock;

    private int price;
};