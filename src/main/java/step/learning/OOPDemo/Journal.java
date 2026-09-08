package step.learning.OOPDemo;

public class Journal extends Literature implements IPeriodic {
    private int number;

    public Journal() {
    }

    public Journal(String title, int number) {
        super.setTitle(title);
        this.setNumber(number);
    }

    public int getNumber() {
        return number;
    }

    public final void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String getPeriod() {
        return "One per month";
    }


    @Override
    public String card() {
        return String.format(
                "Journal '%s' No %d",
                super.getTitle(),
                this.getNumber()
        );
    }
}