package kiselev.anton.booklist.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import java.util.NoSuchElementException;
import kiselev.anton.booklist.model.Book;
import kiselev.anton.booklist.dao.BookDao;
import org.springframework.stereotype.Service;
import kiselev.anton.booklist.dao.dto.BookFilter;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;
    private final BookFilter bookFilter;

    public Long create(Book book) {
        return bookDao.create(book);
    }

    public Book findById(Long id) { return bookDao.findById(id); }

    public void deleteById(Long id) { bookDao.deleteById(id); }

    @Transactional
    public void update(Long id, Book newBook) {
        if (id == null) {
            throw new IllegalStateException("Не указан идентификатор обновляемой книги");
        }

        Book oldBook = bookDao.findById(id);
        if (oldBook == null) {
            throw new NoSuchElementException("Книга с id " + id + " для обновления не найдена");
        }

        Book updatedBook = new Book(
                id,
                newBook.getVendorcode(),
                newBook.getTitle(),
                newBook.getYear(),
                newBook.getBrand(),
                newBook.getStock(),
                newBook.getPrice()
        );

        bookDao.update(updatedBook);
    }

    public List<Book> findAll() { return bookDao.findAll();  }

    public List<Book> findFiltered(BookFilter filter) {
        return bookDao.findFiltered(filter);
    }
}

