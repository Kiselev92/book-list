package kiselev.anton.booklist.service;

import lombok.RequiredArgsConstructor;
import kiselev.anton.booklist.model.Book;
import kiselev.anton.booklist.dao.BookDao;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;

    public Long create(Book book) {
        return bookDao.create(book);
    }

    public Book findById(Long id) { return bookDao.findById(id); }

    public void deleteById(Long id) { bookDao.deleteById(id); }

    public void update(Book book) {
        Long id = book.getId();
        if (id == null) {
            throw new IllegalStateException("Не указан идентификатор обновляемого уведомления. " + book);
        }
        Book oldNotification = bookDao.findById(id);
        if (oldNotification == null) {
            throw new NoSuchElementException("Notification with id " + id + " for update not found");
        }
        bookDao.update(id, book);
    }
}
