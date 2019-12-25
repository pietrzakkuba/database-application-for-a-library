package sql.tables;

public class ShiftsTable {
    private int employee_id;
    private int schedule_id;
    private int time_from;
    private int time_to;

    public ShiftsTable(int employee_id, int schedule_id, int time_from, int time_to) {
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

    public int getTime_from() {
        return time_from;
    }

    public void setTime_from(int time_from) {
        this.time_from = time_from;
    }

    public int getTime_to() {
        return time_to;
    }

    public void setTime_to(int time_to) {
        this.time_to = time_to;
    }
}
