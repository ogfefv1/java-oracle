package step.learning.OOPDemo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper extends Literature {
    public static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd.MM.yyyy");
    private Date date;

    public Newspaper() {
    }

    public Newspaper(String title, Date date) {
        super.setTitle(title);
        this.setDate(date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String card() {
        return String.format(
                "Newspaper '%s' from %s",
                super.getTitle(),
                dateFormat.format(this.getDate())
        );
    }
}