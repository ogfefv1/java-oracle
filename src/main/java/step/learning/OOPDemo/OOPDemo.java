package step.learning.OOPDemo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OOPDemo {
    public void run() {
        List<Literature> library = new ArrayList<>();
        library.add(new Book("The Art of Programming", "D. Knuth"));
        library.add(new Book("Jokes for Long-Haul Truckers", "A. Diesel"));
        library.add(new Book("101 Ways to Cook Instant Noodles", "M. Ramen"));
        library.add(new Book("How to Talk to Your Cat About Taxes", "P. Whiskers"));
        library.add(new Booklet("Safety Rules at the Workshop", "Step IT Academy"));
        library.add(new Booklet("How to Assemble a Wardrobe", "IKEA Press"));
        library.add(new Journal("Science Today", 42));
        try {
            library.add(
                    new Newspaper("The Daily Compiler",
                            Newspaper.dateFormat.parse("04.09.2026"))
            );
        }
        catch (ParseException ex) {
            System.getLogger(OOPDemo.class.getName())
                    .log(System.Logger.Level.ERROR, ex.getMessage());
        }
        for (Literature lit : library) {
            System.out.println( lit.card() );
        }
    }
}