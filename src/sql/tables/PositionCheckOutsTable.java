package sql.tables;

public class PositionCheckOutsTable {
    private int copy_id;
    private int checkout_id;

    public PositionCheckOutsTable(int copy_id, int checkout_id) {
        this.copy_id = copy_id;
        this.checkout_id = checkout_id;
    }

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
    }

    public int getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(int checkout_id) {
        this.checkout_id = checkout_id;
    }
}
