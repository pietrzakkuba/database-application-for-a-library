package sql.tables;

public class AffiliatesTable {
    private int number;
    private String address;
    private int opening_hours_from;
    private int opening_hours_to;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public AffiliatesTable(int number, String address, int opening_hours_from, int opening_hours_to) {
        this.number = number;
        this.address = address;
        this.opening_hours_from = opening_hours_from;
        this.opening_hours_to = opening_hours_to;
    }
}
