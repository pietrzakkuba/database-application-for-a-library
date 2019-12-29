package sql.tables;

import java.sql.Date;

public class CheckOutsTable {
    private int checkout_id;
    private String reader_first_name;
    private String reader_last_name;
    private String book_title;
    private int copy_id;
    private Date check_out_date;
    private int check_out_period;
    private Date return_date;

    public CheckOutsTable(int checkout_id, String reader_first_name, String reader_last_name, String book_title, int copy_id, Date check_out_date, int check_out_period, Date return_date) {
        this.checkout_id = checkout_id;
        this.reader_first_name = reader_first_name;
        this.reader_last_name = reader_last_name;
        this.book_title = book_title;
        this.copy_id = copy_id;
        this.check_out_date = check_out_date;
        this.check_out_period = check_out_period;
        this.return_date = return_date;
    }

    public String getAll() {
        return String.format("%d %s %s %s %d %s %d %s", this.checkout_id, this.reader_first_name, this.reader_last_name, this.book_title, this.copy_id, this.check_out_date, this.check_out_period, this.return_date);
    }

    public int getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(int checkout_id) {
        this.checkout_id = checkout_id;
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

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
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

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }
}
