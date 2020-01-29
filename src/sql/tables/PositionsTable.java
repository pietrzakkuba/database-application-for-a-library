package sql.tables;

public class PositionsTable {
    private String name;
    private double minimal_hourly_rate;
    private int number_of_employees;
    private int id;

    public PositionsTable(String name, double minimal_hourly_rate, int number_of_employees, int id) {
        this.name = name;
        this.minimal_hourly_rate = minimal_hourly_rate;
        this.number_of_employees = number_of_employees;
        this.id = id;
    }

    public String getAll() {
        return String.format("%d %s %s %d", this.id, this.name, this.minimal_hourly_rate, this.number_of_employees);
    }

    public String getToChoose() {
        return String.format("%s", this.name, this.minimal_hourly_rate, this.number_of_employees);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinimal_hourly_rate() {
        return minimal_hourly_rate;
    }

    public void setMinimal_hourly_rate(double minimal_hourly_rate) {
        this.minimal_hourly_rate = minimal_hourly_rate;
    }

    public int getNumber_of_employees() {
        return number_of_employees;
    }

    public void setNumber_of_employees(int number_of_employees) {
        this.number_of_employees = number_of_employees;
    }
}
