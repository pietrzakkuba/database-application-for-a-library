package sql.tables;

public class SectionsTable {
    private int section_id;
    private int affiliate_id;
    private String name;
    private String short_name;

    public SectionsTable(int section_id, int affiliate_id, String name, String short_name) {
        this.section_id = section_id;
        this.affiliate_id = affiliate_id;
        this.name = name;
        this.short_name = short_name;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getAffiliate_id() {
        return affiliate_id;
    }

    public void setAffiliate_id(int affiliate_id) {
        this.affiliate_id = affiliate_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
