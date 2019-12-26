package sql.tables;

import java.sql.Date;

public class EmployeesTable {
    private int id;
    private String position;
    private String first_name;
    private String last_name;
    private Date date_of_employment;
    private Date date_of_signing_cotract;
    private double hourly_rate;
    private Date date_of_contract_expiration;

    public EmployeesTable(String first_name, String last_name, Date date_of_employment, Date date_of_signing_cotract, Date date_of_contract_expiration, double hourly_rate, int id, String position) {
        this.id = id;
        this.position = position;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_employment = date_of_employment;
        this.date_of_signing_cotract = date_of_signing_cotract;
        this.hourly_rate = hourly_rate;
        this.date_of_contract_expiration = date_of_contract_expiration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public Date getDate_of_employment() {
        return date_of_employment;
    }

    public void setDate_of_employment(Date date_of_employment) {
        this.date_of_employment = date_of_employment;
    }

    public Date getDate_of_signing_cotract() {
        return date_of_signing_cotract;
    }

    public void setDate_of_signing_cotract(Date date_of_signing_cotract) {
        this.date_of_signing_cotract = date_of_signing_cotract;
    }

    public double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public Date getDate_of_contract_expiration() {
        return date_of_contract_expiration;
    }

    public void setDate_of_contract_expiration(Date date_of_contract_expiration) {
        this.date_of_contract_expiration = date_of_contract_expiration;
    }
}
