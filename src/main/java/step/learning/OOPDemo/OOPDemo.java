package step.learning.OOPDemo;

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
        for (Literature lit : library) {
            System.out.println( lit.card() );
        }
    }
}