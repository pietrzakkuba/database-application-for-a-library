package sql.tables;

import java.sql.Date;

public class OrdersTable {
    private int id;
    private int reader_id;
    private Date order_date;

    public OrdersTable(Date order_date, int id, int reader_id) {
        this.id = id;
        this.reader_id = reader_id;
        this.order_date = order_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReader_id() {
        return reader_id;
    }

    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
}
