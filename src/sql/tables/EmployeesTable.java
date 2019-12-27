package sql.tables;

import java.sql.Date;

public class EmployeesTable {
    private int id;
    private String first_name;
    private String last_name;
    private String position;
    private String affiliate;
    private Date date_of_employment;
    private Date date_of_signing_contract;
    private Date date_of_contract_expiration;
    private double hourly_rate;

    public EmployeesTable(int id, String first_name, String last_name, String position, String affiliate, Date date_of_employment, Date date_of_signing_contract, Date date_of_contract_expiration, double hourly_rate) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.position = position;
        this.affiliate = affiliate;
        this.date_of_employment = date_of_employment;
        this.date_of_signing_contract = date_of_signing_contract;
        this.date_of_contract_expiration = date_of_contract_expiration;
        this.hourly_rate = hourly_rate;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public Date getDate_of_employment() {
        return date_of_employment;
    }

    public void setDate_of_employment(Date date_of_employment) {
        this.date_of_employment = date_of_employment;
    }

    public Date getDate_of_signing_contract() {
        return date_of_signing_contract;
    }

    public void setDate_of_signing_contract(Date date_of_signing_contract) {
        this.date_of_signing_contract = date_of_signing_contract;
    }

    public Date getDate_of_contract_expiration() {
        return date_of_contract_expiration;
    }

    public void setDate_of_contract_expiration(Date date_of_contract_expiration) {
        this.date_of_contract_expiration = date_of_contract_expiration;
    }

    public double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }
}
