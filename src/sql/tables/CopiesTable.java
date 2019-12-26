package sql.tables;

import java.sql.Date;

public class CopiesTable {
    private int copy_id;
    private int section_id;
    private int book_id;
    private boolean availability;
    private String type_of_cover;
    private Date release_year;
    private int release_number;

    public CopiesTable(int copy_id, boolean availability, int release_number, Date release_year, String type_of_cover, int book_id, int section_id) {
        this.copy_id = copy_id;
        this.section_id = section_id;
        this.book_id = book_id;
        this.availability = availability;
        this.type_of_cover = type_of_cover;
        this.release_year = release_year;
        this.release_number = release_number;
    }

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getType_of_cover() {
        return type_of_cover;
    }

    public void setType_of_cover(String type_of_cover) {
        this.type_of_cover = type_of_cover;
    }

    public Date getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Date release_year) {
        this.release_year = release_year;
    }

    public int getRelease_number() {
        return release_number;
    }

    public void setRelease_number(int release_number) {
        this.release_number = release_number;
    }
}
