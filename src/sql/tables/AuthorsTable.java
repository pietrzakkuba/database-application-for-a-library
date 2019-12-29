package sql.tables;

import fxapp.Main;

import java.sql.Date;

public class AuthorsTable {
    private int id;
    private String first_name;
    private String last_name;
    private String pseudonym;
    private Date date_of_birth;
    private Date date_of_death;
    private String nationality;

    public AuthorsTable(int id, String first_name, String last_name, String pseudonym, Date date_of_birth, Date date_of_death, String nationality) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.pseudonym = pseudonym;
        this.date_of_birth = date_of_birth;
        this.date_of_death = date_of_death;
        this.nationality = nationality;
    }

    public String getAll() {
        return String.format("%d %s %s %s %s %s %s", this.id, this.first_name, this.last_name, this.pseudonym, this.date_of_birth, this.date_of_death, this.nationality);
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

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Date getDate_of_death() {
        return date_of_death;
    }

    public void setDate_of_death(Date date_of_death) {
        this.date_of_death = date_of_death;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

