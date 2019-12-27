package sql.tables;

import java.sql.Date;

public class ShiftScheduleTable {
    private int id;
    private boolean is_valid;
    private String affiliate_name;
    private String schedule_name;
    private Date valid_from;
    private Date valid_to;
    private int number_of_shifts;

    public ShiftScheduleTable(int id, boolean is_valid, String affiliate_name, String schedule_name, Date valid_from, Date valid_to, int number_of_shifts) {
        this.id = id;
        this.is_valid = is_valid;
        this.affiliate_name = affiliate_name;
        this.schedule_name = schedule_name;
        this.valid_from = valid_from;
        this.valid_to = valid_to;
        this.number_of_shifts = number_of_shifts;
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

    public String getAffiliate_name() {
        return affiliate_name;
    }

    public void setAffiliate_name(String affiliate_name) {
        this.affiliate_name = affiliate_name;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
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

    public int getNumber_of_shifts() {
        return number_of_shifts;
    }

    public void setNumber_of_shifts(int number_of_shifts) {
        this.number_of_shifts = number_of_shifts;
    }
}
