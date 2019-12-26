package sql.tables;

public class PositionOrdersTable {
    private int book_id;
    private int order_id;

    public PositionOrdersTable(int book_id, int order_id) {
        this.book_id = book_id;
        this.order_id = order_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
