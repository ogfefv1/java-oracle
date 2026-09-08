package step.learning.AsyncDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TaskDemo {

    private ExecutorService pool =
            Executors.newFixedThreadPool(3);

    public void run() {
        Callable<String> getHello = () -> {
            System.out.println("Task start sleeping");
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
            System.out.println("Task finish sleeping");
            return "Hello";
        };
        Future<String> task = pool.submit(getHello);
        String taskResult;
        try {
            taskResult = task.get();
            System.out.println("Task got: " + taskResult);
        }
        catch (Exception ex) {
            System.out.println("Task caught: " + ex.getMessage());
        }

        taskChaining();
        calcPercent();
        calcInflation();

        try {
            pool.shutdown();
            pool.awaitTermination(100, TimeUnit.MILLISECONDS);
            pool.shutdownNow();
        }
        catch (InterruptedException ex) {
            System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
        }
    }

    private void taskChaining() {
        Supplier<String> supplier = () -> {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ex) {
                System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
            }
            return "Hello";
        };
        Function<String, String> fn = (String s) -> {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ex) {
                System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
            }
            return s + " World";
        };
        Function<String, String> excl = (String s) -> s + "!";
        Consumer<String> action = (String s) -> {
            System.out.println(s);
        };

        CompletableFuture chain =
                CompletableFuture
                        .supplyAsync(supplier, pool)
                        .thenApply(fn)
                        .thenApply(excl)
                        .thenApply(excl)
                        .thenAccept(action);

        try {
            chain.get();    // очікування завершення усієї "нитки"
        }
        catch (ExecutionException | InterruptedException ex) {
            System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
        }
        // задача: виконати задачу "інфляція" з використанням
        // ниток коду: одержання відсотка - коригування суми
    }

    private void calcPercent() {
        Future[] tasks = new Future[12];
        for (int i = 1; i <= 12; ++i) {
            tasks[i-1] = pool.submit(new Percent(i));
        }
        try {
            double sum = 100.0;
            for (int i = 1; i <= 12; ++i) {
                double p = (double) tasks[i-1].get();
                sum *= (1.0 + p / 100.0);
                System.out.println("Task " + i + " got " + p +
                        " sum = " + sum
                );
            }
        }
        catch (InterruptedException | ExecutionException ex) {
            System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
        }
    }

    // задача: виконати задачу "інфляція" з використанням
    // ниток коду: одержання відсотка - коригування суми
    private void calcInflation() {
        double sum = 100.0;
        for (int i = 1; i <= 12; i++) {
            final int month = i;
            Supplier<Double> getPercent = () -> {
                try {
                    return new Percent(month).call();
                }
                catch (Exception ex) {
                    System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
                    return 0.0;
                }
            };
            final double prevSum = sum;
            Function<Double, Double> correctSum = (Double p) -> prevSum * (1.0 + p / 100.0);

            CompletableFuture<Double> chain =
                    CompletableFuture
                            .supplyAsync(getPercent, pool)
                            .thenApply(correctSum);

            try {
                sum = chain.get();
                System.out.println("Month " + month + " sum = " + sum);
            }
            catch (InterruptedException | ExecutionException ex) {
                System.getLogger(TaskDemo.class.getName()).log(System.Logger.Level.ERROR, ex.getMessage());
            }
        }
    }

    class Percent implements Callable<Double> {
        private final int month;

        public Percent(int month) {
            this.month = month;
        }

        @Override
        public Double call() throws Exception {
            try {
                System.out.println("Start loading month " + month);
                Thread.sleep(500);
                System.out.println("Finish loading month " + month);
                return 1.0 * month;
            }
            catch (InterruptedException ex) {
                System.out.println("Error loading month " + month);
                throw ex;
            }
        }
    }
}