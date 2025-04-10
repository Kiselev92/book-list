package kiselev.anton.booklist.port;

import kiselev.anton.booklist.model.Book;
import kiselev.anton.booklist.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public String create(@ModelAttribute Book book) {
        bookService.create(book);
        return "redirect:/book/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book/details";  // Шаблон src/main/webapp/WEB-INF/views/book/details.html
    }

    @PutMapping
    public void update(@RequestBody Book book) { bookService.update(book); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

 /*   private static BookRequest toModel(BookRequest request) {
        return BookRequest.builder()
                .id(request.getId())
                .vendorCode(request.getVendorCode())
                .title(request.getTitle())
                .year(request.getYear())
                .brand(request.getBrand())
                .stock(request.getStock())
                .price(request.getPrice())
                .build();
    }*/
}




