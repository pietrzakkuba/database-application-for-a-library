package sql.tables;

import java.util.ArrayList;

public class AffiliatesTable {
    private int id_number;
    private String address;
    private int opening_hours_from;
    private int opening_hours_to;
    private int number_of_employees;

    public AffiliatesTable(int id_number, String address, int opening_hours_from, int opening_hours_to, int number_of_employees) {
        this.id_number = id_number;
        this.address = address;
        this.opening_hours_from = opening_hours_from;
        this.opening_hours_to = opening_hours_to;
        this.number_of_employees = number_of_employees;
    }

    public String getAll() {
        return String.format("%d %s %d %d %d", this.id_number, this.address, this.opening_hours_from, this.opening_hours_to, this.number_of_employees);
    }

    public String getToChoose() {
        return String.format("%d %s", this.id_number, this.address);
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOpening_hours_from() {
        return opening_hours_from;
    }

    public void setOpening_hours_from(int opening_hours_from) {
        this.opening_hours_from = opening_hours_from;
    }

    public int getOpening_hours_to() {
        return opening_hours_to;
    }

    public void setOpening_hours_to(int opening_hours_to) {
        this.opening_hours_to = opening_hours_to;
    }

    public int getNumber_of_employees() {
        return number_of_employees;
    }

    public void setNumber_of_employees(int number_of_employees) {
        this.number_of_employees = number_of_employees;
    }
}
