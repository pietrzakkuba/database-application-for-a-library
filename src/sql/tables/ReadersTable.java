package sql.tables;

import java.sql.Date;

public class ReadersTable {
    private int id;
    private String first_name;
    private String last_name;
    private Date year_of_birth;
    private Date date_of_signing_in;
    private int number_of_borrowed_books;

    public ReadersTable(int id, String first_name, String last_name, Date year_of_birth, Date date_of_signing_in, int number_of_borrowed_books) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.year_of_birth = year_of_birth;
        this.date_of_signing_in = date_of_signing_in;
        this.number_of_borrowed_books = number_of_borrowed_books;
    }

    public String getAll() {
        return String.format("%d %s %s %s %s %d", this.id, this.first_name, this.last_name, this.year_of_birth, this.date_of_signing_in, this.number_of_borrowed_books);
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

    public Date getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(Date year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public Date getDate_of_signing_in() {
        return date_of_signing_in;
    }

    public void setDate_of_signing_in(Date date_of_signing_in) {
        this.date_of_signing_in = date_of_signing_in;
    }

    public int getNumber_of_borrowed_books() {
        return number_of_borrowed_books;
    }

    public void setNumber_of_borrowed_books(int number_of_borrowed_books) {
        this.number_of_borrowed_books = number_of_borrowed_books;
    }
}

