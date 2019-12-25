package sql.tables;

public class PositionsTable {
    private String name;
    private double minimal_hourly_rate;

    public PositionsTable(String name, double minimal_hourly_rate) {
        this.name = name;
        this.minimal_hourly_rate = minimal_hourly_rate;
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
}
