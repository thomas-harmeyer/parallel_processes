import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimeFinder {
    // Sieve of Eratosthenes algorithm
    // This method uses the Sieve of Eratosthenes algorithm to find all prime numbers between the start and end range. Pretty standard
    public static List<Integer> findPrimes(int start, int end) {
        boolean[] primes = new boolean[end + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        for (int i = 2; i * i <= end; i++) {
            if (primes[i]) {
                for (int j = i * i; j <= end; j += i) {
                    primes[j] = false;
                }
            }
        }

        List<Integer> primeList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (primes[i]) {
                primeList.add(i);
            }
        }
        return primeList;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // range of numbers to find primes for
        int range = (int) Math.pow(10, 8);
        // number of threads to use
        int threadCount = 8;

        // create an executor service with the specified number of threads
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<List<Integer>>> futures = new ArrayList<>();
        int interval = range / threadCount;

        // divide the range into intervals and assign each interval to a different thread
        for (int i = 0; i < threadCount; i++) {
            final int start = i * interval + 1;
            int end = (i + 1) * interval;
            // if this is the last thread, make sure to include the remaining numbers in the range
            if (i == threadCount - 1) {
                end = range;
            }
            final int endFinal = end;
            Future<List<Integer>> future = executor.submit(() -> findPrimes(start, endFinal));
            futures.add(future);
        }

        try (FileWriter writer = new FileWriter("primes.txt")) {
            // combine the results from all threads into a single list
            List<Integer> finalList = new ArrayList<>();
            for (Future<List<Integer>> future : futures) {
                finalList.addAll(future.get());
            }
            long sum = 0;
            // get the sum of the primes
            for (Integer prime : finalList) {
                sum += prime;
            }
            long endTime = System.currentTimeMillis();

            // print the results to the console
            write.write((endTime - startTime) + "ms ");
            write.write(finalList.size() + " ");
            write.write(sum + "\n");

            int topTen = Math.min(finalList.size(), 10);
            for (int i = finalList.size() - topTen; i < finalList.size(); i++) {
                write.write(finalList.get(i) + " ");
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }


}
