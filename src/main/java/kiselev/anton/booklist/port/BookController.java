package kiselev.anton.booklist.port;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import java.util.NoSuchElementException;
import kiselev.anton.booklist.model.Book;
import org.springframework.web.bind.annotation.*;
import kiselev.anton.booklist.dao.dto.BookFilter;
import org.springframework.stereotype.Controller;
import kiselev.anton.booklist.service.BookService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/list")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "/list";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Validated Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        bookService.create(book);
        return "redirect:/book/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        try {
            Book book = bookService.findById(id);
            model.addAttribute("book", book);
        } catch (NoSuchElementException e) {
            model.addAttribute("book", null);
        }
        return "findById";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/edit";
    }

    @PostMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book,
                             @PathVariable("id") Long id,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        bookService.update(id, book);
        return "redirect:/book/list";
    }

   @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/book/list";
    }

    @GetMapping("/filter")
    public String filterBooks(BookFilter filter, Model model) {
        model.addAttribute("books", bookService.findFiltered(filter));
        return "/list";
    }

}






