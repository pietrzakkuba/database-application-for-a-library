package sql.tables;

import java.sql.Date;

public class CheckOutsTable {
    private int checkout_id;
    private int reader_id;
    private Date check_out_date;
    private int check_out_period;
    private int return_date;

    public CheckOutsTable(int checkout_id, int reader_id, Date check_out_date, int check_out_period, int return_date) {
        this.checkout_id = checkout_id;
        this.reader_id = reader_id;
        this.check_out_date = check_out_date;
        this.check_out_period = check_out_period;
        this.return_date = return_date;
    }

    public int getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(int checkout_id) {
        this.checkout_id = checkout_id;
    }

    public int getReader_id() {
        return reader_id;
    }

    public void setReader_id(int reader_id) {
        this.reader_id = reader_id;
    }

    public Date getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        this.check_out_date = check_out_date;
    }

    public int getCheck_out_period() {
        return check_out_period;
    }

    public void setCheck_out_period(int check_out_period) {
        this.check_out_period = check_out_period;
    }

    public int getReturn_date() {
        return return_date;
    }

    public void setReturn_date(int return_date) {
        this.return_date = return_date;
    }
}
