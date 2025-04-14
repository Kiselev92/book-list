package kiselev.anton.booklist.dao.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BookFilter {
    private String title;
    private String brand;
    private Integer year;
    private Integer limit = 10;
    private Integer offset = 0;
}
