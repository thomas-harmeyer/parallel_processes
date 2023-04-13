package src;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
  private static final int NUM_PRESENTS = 50;
  private static final int NUM_SERVANTS = 4;

  static final AtomicInteger tasksCompleted = new AtomicInteger(0);

  public static void printProgress() {
    int totalTasks = NUM_PRESENTS * 2; // Each servant adds and removes presents
    double percentageCompleted = (double) tasksCompleted.get() / totalTasks * 100;
    System.out.printf("Progress: %.2f%%%n", percentageCompleted);
  }

  public static void main(String[] args) {
    PresentLinkedList presentList = new PresentLinkedList();
    ExecutorService executor = Executors.newFixedThreadPool(NUM_SERVANTS);

    int[] allPresents = generateUniqueRandomArray(NUM_PRESENTS);

    int chunkSize = NUM_PRESENTS / (NUM_SERVANTS * 2); // Divide by 2 to make the number of presents added and removed
                                                       // the same
    for (int i = 0; i < NUM_SERVANTS; i++) {
      int[] presentsToAdd = Arrays.copyOfRange(allPresents, i * chunkSize, (i + 1) * chunkSize);
      int[] presentsToRemove = Arrays.copyOfRange(allPresents, i * chunkSize, (i + 1) * chunkSize);
      Servant servant = new Servant(presentList, presentsToAdd, presentsToRemove);
      executor.submit(servant);
    }

    executor.shutdown();

    Thread progressThread = new Thread(() -> {
      while (!executor.isTerminated()) {
        printProgress();
        try {
          Thread.sleep(1000); // Update progress every second
        } catch (InterruptedException e) {
          System.out.println("Progress thread interrupted.");
        }
      }
      printProgress(); // Print the final progress after tasks are completed
    });

    progressThread.start();
    // loop till progessThread is finished
    while (progressThread.isAlive()) {
      try {
        progressThread.join();
      } catch (InterruptedException e) {
        System.out.println("Main thread interrupted.");
      }
    }

    System.out.println("All servants have finished their tasks.");
  }

  private static int[] generateUniqueRandomArray(int size) {
    int[] arr = new int[size];
    for (int i = 0; i < size; i++) {
      arr[i] = i + 1;
    }

    Random random = new Random();
    for (int i = size - 1; i > 0; i--) {
      int index = random.nextInt(i + 1);
      int temp = arr[index];
      arr[index] = arr[i];
      arr[i] = temp;
    }

    return arr;
  }
}
