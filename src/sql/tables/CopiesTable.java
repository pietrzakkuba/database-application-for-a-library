package sql.tables;

import java.sql.Date;

public class CopiesTable {
    private int copy_id;
    private String book_title;
    private boolean availability;
    private String section;
    private String affiliate;
    private int release_number;
    private Date release_year;
    private String type_of_cover;
    private int book_id;
    private int section_id;

    public CopiesTable(int copy_id, String book_title, boolean availability, String section, String affiliate, int release_number, Date release_year, String type_of_cover, int book_id, int section_id) {
        this.copy_id = copy_id;
        this.book_title = book_title;
        this.availability = availability;
        this.section = section;
        this.affiliate = affiliate;
        this.release_number = release_number;
        this.release_year = release_year;
        this.type_of_cover = type_of_cover;
        this.book_id = book_id;
        this.section_id = section_id;
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

    public String getAll() {
        return String.format("%d %s %s %s %s %d %s %s", this.copy_id, this.book_title, this.availability, this.section, this.affiliate, this.release_number, this.release_year, this.type_of_cover);
    }

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public int getRelease_number() {
        return release_number;
    }

    public void setRelease_number(int release_number) {
        this.release_number = release_number;
    }

    public Date getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Date release_year) {
        this.release_year = release_year;
    }

    public String getType_of_cover() {
        return type_of_cover;
    }

    public void setType_of_cover(String type_of_cover) {
        this.type_of_cover = type_of_cover;
    }
}
