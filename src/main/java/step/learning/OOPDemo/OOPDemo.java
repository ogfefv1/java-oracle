package step.learning.OOPDemo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OOPDemo {
    private final List<Literature> library = new ArrayList<>();
    public void run() {
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
            boolean isFound = false;
            Method[] methods = lit.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Card.class)) {
                    try {
                        Card card = method.getAnnotation( Card.class );

                        System.out.printf(
                                "%s %s%n",
                                card.value(),
                                method.invoke(lit)
                        );
                        isFound = true;
                    }
                    catch (IllegalAccessException | InvocationTargetException ex) {
                        System.getLogger(OOPDemo.class.getName())
                                .log(System.Logger.Level.ERROR, ex.getMessage());
                    }

                }
            }
            if ( ! isFound ) {
                System.out.println( lit.card() );
            }
        }
        printPeriodic();
    }

    private void printPeriodic() {
        System.out.println("--- PERIODIC ---");
        for (Literature lit : library) {
            if (lit.getClass().isAnnotationPresent(Periodic.class)) {
                Periodic periodic = lit.getClass().getAnnotation(Periodic.class);
                System.out.printf(
                        "%s  %s%n",
                        lit.card(),
                        periodic.value()
                );
            }
        }
        System.out.println("--- END PERIODIC ---");
    }
}