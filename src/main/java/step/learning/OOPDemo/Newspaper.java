package step.learning.OOPDemo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper extends Literature implements IPeriodic {
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

    public final void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getPeriod() {
        return "One per day";
    }

    @Override
    public String card() {
        return String.format(
                "Newspaper '%s' From %s",
                super.getTitle(),
                Newspaper.dateFormat.format( this.getDate() )
        );
    }
}