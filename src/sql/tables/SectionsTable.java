package sql.tables;

public class SectionsTable {
    private int section_id;
    private String section_name;
    private String section_short_name;
    private int number_of_books;
    private String affiliate_name;

    public SectionsTable(int section_id, String section_name, String section_short_name, int number_of_books, String affiliate_name) {
        this.section_id = section_id;
        this.section_name = section_name;
        this.section_short_name = section_short_name;
        this.number_of_books = number_of_books;
        this.affiliate_name = affiliate_name;
    }

    public String getAll() {
        return String.format("%d %s %s %d %s", this.section_id, this.section_name, this.section_short_name, this.number_of_books, this.affiliate_name);
    }

    public String getToChoose() {
        return String.format("%s %s, %s", this.section_id, this.section_name, this.section_short_name, this.affiliate_name);
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_short_name() {
        return section_short_name;
    }

    public void setSection_short_name(String section_short_name) {
        this.section_short_name = section_short_name;
    }

    public int getNumber_of_books() {
        return number_of_books;
    }

    public void setNumber_of_books(int number_of_books) {
        this.number_of_books = number_of_books;
    }

    public String getAffiliate_name() {
        return affiliate_name;
    }

    public void setAffiliate_name(String affiliate_name) {
        this.affiliate_name = affiliate_name;
    }
}
