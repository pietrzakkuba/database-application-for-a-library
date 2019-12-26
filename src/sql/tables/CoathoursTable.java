package sql.tables;

public class CoathoursTable {
    private int book_id;
    private int author_id;

    public CoathoursTable(int book_id, int author_id) {
        this.book_id = book_id;
        this.author_id = author_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
}
