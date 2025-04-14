package kiselev.anton.booklist.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import java.util.NoSuchElementException;
import kiselev.anton.booklist.model.Book;
import kiselev.anton.booklist.dao.dto.BookFilter;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Repository
@RequiredArgsConstructor

public class BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public Long create(Book book) {
        String sql = """
            INSERT INTO books_list (vendorcode, title, year, brand, stock, price)
            VALUES (:vendorcode, :title, :year, :brand, :stock, :price)
            RETURNING id""";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("vendorcode", book.getVendorcode())
                .addValue("title", book.getTitle())
                .addValue("year", book.getYear())
                .addValue("brand", book.getBrand())
                .addValue("stock", book.getStock())
                .addValue("price", book.getPrice());


        return jdbc.queryForObject(sql, params, (rs, rowNum) -> rs.getLong("id"));
    }

    public Book findById(Long id) {
        String sql = """
            SELECT *
            FROM books_list
            WHERE id = :id""";

        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

        return jdbc.query(sql, params, rs -> {
            if (!rs.next()) {
                throw new NoSuchElementException("Book not found with id: " + id);
            }

            return Book.builder()
                    .id(rs.getLong("id"))
                    .vendorcode(rs.getString("vendorcode"))
                    .title(rs.getString("title"))
                    .year(rs.getInt("year"))
                    .brand(rs.getString("brand"))
                    .stock(rs.getInt("stock"))
                    .price(rs.getInt("price"))
                    .build();
        });
    }

    public void deleteById(Long id) {
        String sql = """
                DELETE 
                FROM books_list 
                WHERE id = :id""";

        SqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbc.update(sql, params);

    }

    public void update(Book book) {
        String sql = """
                UPDATE books_list 
                SET vendorcode = :vendorcode, title = :title, year = :year, brand = :brand, stock = :stock, price = :price
                WHERE id = :id""";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("vendorcode", book.getVendorcode())
                .addValue("title", book.getTitle())
                .addValue("year", book.getYear())
                .addValue("brand", book.getBrand())
                .addValue("stock", book.getStock())
                .addValue("price", book.getPrice());

        jdbc.update(sql, params);
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM books_list";
        return jdbc.query(sql, (rs, rowNum) ->
                Book.builder()
                        .id(rs.getLong("id"))
                        .vendorcode(rs.getString("vendorcode"))
                        .title(rs.getString("title"))
                        .year(rs.getInt("year"))
                        .brand(rs.getString("brand"))
                        .stock(rs.getInt("stock"))
                        .price(rs.getInt("price"))
                        .build()
        );
    }

    public List<Book> findFiltered(BookFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM books_list WHERE 1=1");
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filter.getTitle() != null && !filter.getTitle().isBlank()) {
            sql.append(" AND LOWER(title) LIKE LOWER(:title)");
            params.addValue("title", "%" + filter.getTitle() + "%");
        }

        if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
            sql.append(" AND LOWER(brand) LIKE LOWER(:brand)");
            params.addValue("brand", "%" + filter.getBrand() + "%");
        }

        if (filter.getYear() != null) {
            sql.append(" AND year = :year");
            params.addValue("year", filter.getYear());
        }

        sql.append(" ORDER BY id DESC LIMIT :limit OFFSET :offset");
        params.addValue("limit", filter.getLimit());
        params.addValue("offset", filter.getOffset());

        return jdbc.query(sql.toString(), params, (rs, rowNum) ->
                Book.builder()
                        .id(rs.getLong("id"))
                        .vendorcode(rs.getString("vendorcode"))
                        .title(rs.getString("title"))
                        .year(rs.getInt("year"))
                        .brand(rs.getString("brand"))
                        .stock(rs.getInt("stock"))
                        .price(rs.getInt("price"))
                        .build()
        );
    }

}
