package kiselev.anton.booklist.dao;

import lombok.RequiredArgsConstructor;
import kiselev.anton.booklist.model.Book;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Repository
@RequiredArgsConstructor

public class BookDao {

    private final NamedParameterJdbcOperations jdbc;
    // private final RowMapper<Book> bookRowMapper = new BookRowMapper();

    public Long create(Book book) {
        String sql = """
                INSERT INTO books_list (id, vendorCode, title, year, brand, stock, price)
                VALUES (:vendorCode, :title, :year, :brand, :stock, :price)
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("vendorCode", book.getVendorCode())
                .addValue("title", book.getTitle())
                .addValue("year", book.getYear())
                .addValue("brand", book.getBrand())
                .addValue("stock", book.getStock())
                .addValue("price", book.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);
        return (Long) keyHolder.getKeys().get("id");
    }

    public Book findById(Long id) {
        String sql = """
                SELECT *
                FROM books_list
                WHERE id = :id""";

        SqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

        return jdbc.query(sql, params, rs -> {
            rs.next();
            return new Book(
                    rs.getLong("id"),
                    rs.getString("vendorCode"),
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getString("brand"),
                    rs.getInt("stock"),
                    rs.getInt("price")
            );
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

    public void update(Long id, Book book) {
        String sql = """
                UPDATE books_list 
                SET id = :id, vendorCode = :vendorCode, title = :title, year = :year, brand = :brand, stock = :stock, price = :price
                WHERE id = :id""";
        SqlParameterSource params = new MapSqlParameterSource("id", id)
                .addValue("vendorCode", book.getVendorCode())
                .addValue("title", book.getTitle())
                .addValue("year", book.getYear())
                .addValue("brand", book.getBrand())
                .addValue("stock", book.getStock())
                .addValue("price", book.getPrice());

        jdbc.update(sql, params);

/*    static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .id(rs.getLong("id"))
                    .vendorCode(rs.getString("vendorCode"))
                    .title(rs.getString("title"))
                    .year(rs.getInt("year"))
                    .brand(rs.getString("brand"))
                    .stock(rs.getInt("stock"))
                    .price(rs.getInt("price"))
                    .build();
        }
    }*/
    }
}
