package step.learning.OOPDemo;

public class Book extends Literature {
    private String author;

    public Book() {
    }

    public Book(String title, String author) {
        super.setTitle(title);
        this.setAuthor(author);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String card() {
        return String.format(
                "Book '%s' by %s",
                super.getTitle(),
                this.getAuthor()
        );
    }
}