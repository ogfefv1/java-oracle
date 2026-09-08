package step.learning.OOPDemo;

@Periodic("One per month")
public class Journal extends Literature {
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
    public String card() {
        return String.format(
                "Journal '%s' No %d",
                super.getTitle(),
                this.getNumber()
        );
    }
}