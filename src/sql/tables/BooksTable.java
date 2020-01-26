package sql.tables;

public class BooksTable {
    private int id;
    private String title;
    private String author_first_name;
    private String author_last_name;
    private int number_of_copies;
    private int number_of_orders;

    public BooksTable(int id, String title, String author_first_name, String author_last_name, int number_of_copies, int number_of_orders) {
        this.id = id;
        this.title = title;
        this.author_first_name = author_first_name;
        this.author_last_name = author_last_name;
        this.number_of_copies = number_of_copies;
        this.number_of_orders = number_of_orders;
    }

    public String getAll() {
        return String.format("%d %s %s %s %d %d", this.id, this.title, this.author_first_name, this.author_last_name, this.number_of_copies, this.number_of_orders);
    }

    public String getToChoose() {
        return String.format("%s, %s %s", this.title, this.author_first_name, this.author_last_name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_first_name() {
        return author_first_name;
    }

    public void setAuthor_first_name(String author_first_name) {
        this.author_first_name = author_first_name;
    }

    public String getAuthor_last_name() {
        return author_last_name;
    }

    public void setAuthor_last_name(String author_last_name) {
        this.author_last_name = author_last_name;
    }

    public int getNumber_of_copies() {
        return number_of_copies;
    }

    public void setNumber_of_copies(int number_of_copies) {
        this.number_of_copies = number_of_copies;
    }

    public int getNumber_of_orders() {
        return number_of_orders;
    }

    public void setNumber_of_orders(int number_of_orders) {
        this.number_of_orders = number_of_orders;
    }
}
