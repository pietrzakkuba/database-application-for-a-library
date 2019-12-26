package sql.tables;

public class ShiftsTable {
    private int employee_id;
    private int schedule_id;
    private double time_from;
    private double time_to;

    public ShiftsTable(int schedule_id, double time_from, double time_to, int employee_id) {
        this.employee_id = employee_id;
        this.schedule_id = schedule_id;
        this.time_from = time_from;
        this.time_to = time_to;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public double getTime_from() {
        return time_from;
    }

    public void setTime_from(double time_from) {
        this.time_from = time_from;
    }

    public double getTime_to() {
        return time_to;
    }

    public void setTime_to(double time_to) {
        this.time_to = time_to;
    }
}
