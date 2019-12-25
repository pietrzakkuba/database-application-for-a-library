package sql.tables;

import java.sql.Date;

public class ShiftScheduleTable {
    private int id;
    private boolean is_valid;
    private int affiliate_number;
    private String name;
    private Date valid_from;
    private Date valid_to;

    public ShiftScheduleTable(int id, boolean is_valid, int affiliate_number, String name, Date valid_from, Date valid_to) {
        this.id = id;
        this.is_valid = is_valid;
        this.affiliate_number = affiliate_number;
        this.name = name;
        this.valid_from = valid_from;
        this.valid_to = valid_to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public int getAffiliate_number() {
        return affiliate_number;
    }

    public void setAffiliate_number(int affiliate_number) {
        this.affiliate_number = affiliate_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getValid_from() {
        return valid_from;
    }

    public void setValid_from(Date valid_from) {
        this.valid_from = valid_from;
    }

    public Date getValid_to() {
        return valid_to;
    }

    public void setValid_to(Date valid_to) {
        this.valid_to = valid_to;
    }
}
