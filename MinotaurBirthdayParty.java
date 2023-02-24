import java.util.Random;

public class MinotaurBirthdayParty {
    private static final int N = 100; // number of guests
    private static final boolean[] visited = new boolean[N]; // keep track of who has visited
    private static final Random rand = new Random(); // random number generator

    public static void main(String[] args) {
        // initialize all guests as not visited
        for (int i = 0; i < N; i++) {
            visited[i] = false;
        }

        // start threads for each guest
        for (int i = 0; i < N; i++) {
            final int guestNumber = i;
            Thread guest = new Thread(() -> {
                // loop until all guests have visited
                while (!allVisited()) {
                    // invite next guest to labyrinth
                    inviteNextGuest();

                    // wait for invitation to labyrinth
                    waitToBeInvited(guestNumber);

                    // enter labyrinth and eat cupcake or leave it
                    boolean eatCupcake = rand.nextBoolean();
                    if (eatCupcake) {
                        System.out.println("Guest " + guestNumber + " ate the cupcake.");
                    } else {
                        System.out.println("Guest " + guestNumber + " left the cupcake.");
                    }

                    // mark guest as visited
                    visited[guestNumber] = true;
                }

                // announce that all guests have visited
                System.out.println("Guest " + guestNumber + " announces that all guests have visited!");
            });

            guest.start();
        }
    }

    private static synchronized void waitToBeInvited(int guestNumber) {
        while (!visited[guestNumber]) {
            try {
                MinotaurBirthdayParty.class.wait();
            } catch (InterruptedException e) {
                // ignore exception
            }
        }
    }

    private static synchronized void inviteNextGuest() {
        // select next guest at random
        int guestNumber;
        do {
            guestNumber = rand.nextInt(N);
        } while (visited[guestNumber]); // ensure that guest has not already visited

        // notify guest thread
        visited[guestNumber] = true;
        MinotaurBirthdayParty.class.notifyAll();
    }

    private static synchronized boolean allVisited() {
        // check if all guests have visited
        for (boolean b : visited) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
}
