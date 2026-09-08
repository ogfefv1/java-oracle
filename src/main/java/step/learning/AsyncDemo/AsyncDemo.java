package step.learning.AsyncDemo;

import java.util.Random;

public class AsyncDemo implements Runnable {
    private double sum;
    private final Object sumLocker = new Object();
    private int monthes;

    private String resultString;
    private final Object stringLocker = new Object();
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    @Override
    public void run() {
        System.out.println("AsyncDemo started");
        new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println("Async Part");
            }
        }
        ).start();
        sum = 1000.0;
        // calcPercents();
        buildRandomString();
    }

    private void buildRandomString() {
        resultString = "";
        int length = 10;
        for (int i = 1; i <= length; i++) {
            new Thread(new CharAppender(i)).start();
        }
    }

    class CharAppender implements Runnable {
        private final int threadId;

        public CharAppender(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            System.out.println("thread #" + threadId + " started generating char...");
            try {
                Thread.sleep(random.nextInt(300) + 100);
            } catch (InterruptedException ignore) {}

            char ch = ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length()));

            synchronized (stringLocker) {
                resultString += ch;
                System.out.printf("thread #%d added '%c' to illia string: %s, len %d%n",
                        threadId, ch, resultString, resultString.length());
            }
        }
    }

    private void calcPercents() {
        monthes = 12;
        for(int i = 1; i <= 12; ++i) {
            new Thread( new Percent(i) ).start();
        }
    }

    class Percent implements Runnable {
        private final int month;

        public Percent(int month) {
            this.month = month;
        }

        @Override
        public void run() {
            System.out.println("Start loading month " + month);
            double baseSum, endSum;

            try { Thread.sleep(500); }
            catch(InterruptedException ignore) {}
            double p = 10.0;
            double k = (1.0 + p / 100.0);

            synchronized (sumLocker) {
                baseSum = sum;
                sum = endSum = baseSum * k;
            }

            System.out.println("Finish loading month " + month);
            System.out.printf(
                    "Month %d: on begin: %f, on end: %f%n",
                    month, baseSum, endSum);

            synchronized (sumLocker) {
                monthes--;
                if(monthes == 9 || monthes == 6 || monthes == 3) {
                    System.out.println("ILLIA");
                }
                if(monthes == 0) {
                    System.out.println("---Total sum: " + sum );
                    System.out.println("ILLIA");
                }
            }
        }
    }
}