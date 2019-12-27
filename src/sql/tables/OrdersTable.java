package sql.tables;

import java.sql.Date;

public class OrdersTable {
    private int id;
    private Date order_date;
    private String reader_first_name;
    private String reader_last_name;
    private String book_title;

    public OrdersTable(int id, Date order_date, String reader_first_name, String reader_last_name, String book_title) {
        this.id = id;
        this.order_date = order_date;
        this.reader_first_name = reader_first_name;
        this.reader_last_name = reader_last_name;
        this.book_title = book_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getReader_first_name() {
        return reader_first_name;
    }

    public void setReader_first_name(String reader_first_name) {
        this.reader_first_name = reader_first_name;
    }

    public String getReader_last_name() {
        return reader_last_name;
    }

    public void setReader_last_name(String reader_last_name) {
        this.reader_last_name = reader_last_name;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }
}
