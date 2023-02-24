import java.util.concurrent.Semaphore;

public class MinotaurCrystalVase {
    private static final int NUM_GUESTS = 100; // number of guests
    private static Semaphore semaphore = new Semaphore(1);

    static class Guest extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("Guest " + getName() + " is waiting in queue.");
                semaphore.acquire(); // wait for the showroom to be available
                System.out.println("Guest " + getName() + " is entering the showroom.");
                System.out.println("Guest " + getName() + " is exiting the showroom.");
                semaphore.release(); // notify the next guest that the showroom is available
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM_GUESTS; i++) {
            new Guest().start();
        }
    }
}
