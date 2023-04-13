import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class Present {
  int tag;
  Present next;

  Present(int tag) {
    this.tag = tag;
    this.next = null;
  }
}

class ConcurrentLinkedList {
  private final Present head = new Present(Integer.MIN_VALUE);
  private final ReentrantLock lock = new ReentrantLock();

  public void addPresent(Present newPresent) {
    lock.lock();
    try {
      Present current = head;
      while (current.next != null && current.next.tag < newPresent.tag) {
        current = current.next;
      }
      newPresent.next = current.next;
      current.next = newPresent;
    } finally {
      lock.unlock();
    }
  }

  public Present removePresent(int tag) {
    Present removedPresent = null;
    lock.lock();
    try {
      Present current = head;
      while (current.next != null && current.next.tag != tag) {
        current = current.next;
      }
      if (current.next != null) {
        removedPresent = current.next;
        current.next = current.next.next;
      }
    } finally {
      lock.unlock();
    }
    return removedPresent;
  }

  public boolean contains(int tag) {
    lock.lock();
    try {
      Present current = head.next;
      while (current != null && current.tag != tag) {
        current = current.next;
      }
      return current != null;
    } finally {
      lock.unlock();
    }
  }
}

public class BirthdayPresents {
  private static final int NUM_SERVANTS = 4;
  private static final int NUM_PRESENTS = 500000;
  private static final AtomicInteger presentTag = new AtomicInteger();

  public static void main(String[] args) {
    ConcurrentLinkedList list = new ConcurrentLinkedList();
    ExecutorService executor = Executors.newFixedThreadPool(NUM_SERVANTS);
    CountDownLatch latch = new CountDownLatch(NUM_PRESENTS);
    long startTime = System.currentTimeMillis();

    for (int i = 0; i < NUM_SERVANTS; i++) {
      executor.execute(() -> {
        while (presentTag.get() < NUM_PRESENTS) {
          int tag = presentTag.getAndIncrement();
          Present present = new Present(tag);
          list.addPresent(present);

          Present removedPresent = list.removePresent(tag);
          if (removedPresent != null) {
            // System.out.println("Thank you note written for present tag: " +
            // removedPresent.tag);
          }
          latch.countDown();
        }
      });
    }

    try {
      latch.await();
      executor.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println("All thank you notes written!");
    System.out.println("Time taken: " + totalTime + " milliseconds");
  }
}
