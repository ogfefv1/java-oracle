package step.learning.OOPDemo;

public class Booklet extends Literature {
    private String publisher;

    public Booklet() {
    }

    public Booklet(String title, String publisher) {
        super.setTitle(title);
        this.setPublisher(publisher);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String card() {
        return String.format(
                "Booklet '%s', published by %s",
                super.getTitle(),
                this.getPublisher()
        );
    }
}