package sql.tables;

import java.sql.Date;

public class ReadersTable {
    private int id;
    private String first_name;
    private String last_name;
    private Date date_of_signing_in;
    private Date year_of_birth;

    public ReadersTable(int id, String first_name, String last_name, Date date_of_signing_in, Date year_of_birth) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_signing_in = date_of_signing_in;
        this.year_of_birth = year_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDate_of_signing_in() {
        return date_of_signing_in;
    }

    public void setDate_of_signing_in(Date date_of_signing_in) {
        this.date_of_signing_in = date_of_signing_in;
    }

    public Date getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(Date year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
