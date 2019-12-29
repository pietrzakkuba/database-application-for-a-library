package sql.tables;

public class ShiftsTable {
    private String schedule_name;
    private double time_from;
    private double time_to;
    private String employee_first_name;
    private String employee_last_name;
    private double shift_length;

    public ShiftsTable(String schedule_name, double time_from, double time_to, String employee_first_name, String employee_last_name, double shift_length) {
        this.schedule_name = schedule_name;
        this.time_from = time_from;
        this.time_to = time_to;
        this.employee_first_name = employee_first_name;
        this.employee_last_name = employee_last_name;
        this.shift_length = shift_length;
    }

    public String getAll() {
        return String.format("%s %s %s %s %s %s", this.schedule_name, this.time_from, this.time_to, this.employee_first_name, this.employee_last_name, this.shift_length);
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
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

    public String getEmployee_first_name() {
        return employee_first_name;
    }

    public void setEmployee_first_name(String employee_first_name) {
        this.employee_first_name = employee_first_name;
    }

    public String getEmployee_last_name() {
        return employee_last_name;
    }

    public void setEmployee_last_name(String employee_last_name) {
        this.employee_last_name = employee_last_name;
    }

    public double getShift_length() {
        return shift_length;
    }

    public void setShift_length(double shift_length) {
        this.shift_length = shift_length;
    }
}
