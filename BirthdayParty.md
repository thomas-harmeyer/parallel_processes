# Minotaur Birthday Party Simulation

This program simulates the Minotaur Birthday Party game, where the Minotaur invites N guests to his labyrinth one at a time, and each guest decides whether to eat the birthday cupcake or leave it. The goal is for all guests to visit the labyrinth at least once without talking to each other about their visits.

## How to Run

1. Ensure that you have Java installed on your system.
2. Download the `MinotaurBirthdayParty.java` file to your computer.
3. Open a terminal or command prompt and navigate to the directory where the file is located.
4. Compile the program by running the command `javac MinotaurBirthdayParty`.
5. Run the program by running the command `java MinotaurBirthdayParty`.
6. Wait for the program to complete. The program will print messages to the console as each guest enters the labyrinth and when all guests have visited.

## How it Works

The program uses a separate thread for each guest and a shared boolean array to keep track of whether each guest has visited the labyrinth. The `main` method initializes the `visited` array and starts a thread for each guest.

Each guest thread loops until all guests have visited the labyrinth. During each iteration of the loop, the guest thread first invites the next guest to the labyrinth by calling the `inviteNextGuest` method, which selects a guest at random who has not already visited the labyrinth. Then the guest thread waits for an invitation to the labyrinth by calling the `waitToBeInvited` method, which waits until the guest is marked as visited in the `visited` array.

Once the guest is invited to the labyrinth, it decides whether to eat the cupcake or leave it and marks itself as visited in the `visited` array. The program uses a `synchronized` block and the `wait` and `notifyAll` methods to ensure that the guest threads cooperate properly and do not interfere with each other.

The program continues until all guests have visited the labyrinth, at which point each guest thread announces that all guests have visited by printing a message to the console.
