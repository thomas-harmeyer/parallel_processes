# Birthday Presents

## Introduction

The Minotaur's birthday party was a great success, with a large number of guests bringing him presents. After the event, the Minotaur decided to sort the presents and write "Thank you" cards for the guests. He employed four servants to assist him in this task, using a concurrent linked-list data structure to organize the presents and manage the process of writing cards.

This report will discuss the design and implementation of a concurrent linked-list solution, simulating the scenario of the Minotaur's birthday present party, where he received 500,000 presents from his guests. We will analyze the efficiency, correctness, and progress guarantee of our program.

## Design and Implementation

We designed a concurrent linked-list class to store the presents based on their tag numbers in increasing order. The program uses a fixed thread pool with four threads, one for each servant. The servants perform three actions:

1. Add a present to the linked-list at the correct position.
2. Write a "Thank you" card for a guest and remove the present from the list.
3. Check if a present with a specific tag is in the list without adding or removing it.

The program uses a ReentrantLock to ensure safe access to the linked-list while adding or removing presents. The servants perform their tasks without waiting for the unordered bag to be empty, which helps to expedite the process.

## Efficiency

Our concurrent linked-list implementation uses a fixed thread pool with four threads, preventing the excessive creation of threads and reducing overhead. The use of a ReentrantLock ensures that only one thread can access the linked-list at a time, maintaining data integrity and preventing race conditions.

## Correctness

Our implementation ensures that presents are added to the list in the correct order and removed once a "Thank you" card has been written. The program guarantees that no present is added or removed twice, as the servants check for a present's presence in the list before performing their tasks.

## Progress Guarantee

The use of a ReentrantLock ensures that threads are not blocked indefinitely while accessing the linked-list. The lock provides a fairness policy that prevents starvation, allowing each servant to complete their tasks without being delayed indefinitely.

## Results and Conclusion

In the simulation of the Minotaur's birthday present party scenario, our concurrent linked-list solution proved efficient, correct, and provided progress guarantees for the four servants. It allowed the Minotaur and his servants to complete their tasks more quickly than if they had used a sequential approach.

Although our implementation was successful, it is important to note that there is room for improvement. Further optimizations, such as using a more advanced concurrent data structure or employing a better scheduling algorithm for the threads, could lead to even better performance. However, our current solution serves as a strong foundation for managing the Minotaur's birthday present party tasks.

# Mars Rover

## Introduction

The next-generation Mars Rover is equipped with a multicore CPU and eight temperature sensors responsible for measuring the atmospheric temperature on Mars. This report discusses the design and implementation of an atmospheric temperature reading module that collects temperature data from the sensors, stores it in shared memory, and compiles hourly reports. The report will also address the efficiency, correctness, and progress guarantee of the implemented solution.

## Design and Implementation

Our solution uses a fixed thread pool with eight threads, one for each temperature sensor. Each sensor thread collects temperature readings at regular intervals (every minute) and stores the data in a shared memory space. The atmospheric temperature module compiles an hourly report containing the top 5 highest and lowest temperature readings, as well as the 10-minute interval with the largest temperature difference.

To handle concurrent data access, we use a ConcurrentLinkedQueue and a ReentrantLock. The ConcurrentLinkedQueue allows multiple threads to add temperature readings without blocking each other, while the ReentrantLock ensures that only one thread generates the report at a time.

## Efficiency

The program utilizes a fixed thread pool to prevent excessive thread creation and reduce overhead. The use of a ConcurrentLinkedQueue and ReentrantLock ensures that threads can safely access the shared memory region without causing delays or missed readings. The module efficiently calculates the top 5 highest and lowest temperatures, as well as the interval with the largest temperature difference.

## Correctness

Our implementation guarantees that all temperature readings are collected, stored, and processed correctly. It ensures that all threads complete their tasks before generating the hourly report, maintaining data integrity and providing accurate results.

## Progress Guarantee

The use of a CountDownLatch ensures that the main thread waits for all sensor threads to complete before generating the report. The ConcurrentLinkedQueue and ReentrantLock guarantee that threads are not blocked indefinitely while accessing shared memory, allowing each sensor to continue collecting data and ensuring the progress of the program.

## Results and Conclusion

In our simulation, the Mars Rover atmospheric temperature reading module successfully collects and processes temperature data using a multithreaded approach. The module efficiently manages concurrent data access, guarantees the correctness of the results, and ensures progress in the program execution.

Our solution demonstrates the effectiveness of using a multithreaded approach for handling temperature readings on the Mars Rover. However, there may be room for improvement through the use of more advanced concurrent data structures or thread scheduling algorithms. Regardless, our current implementation serves as a robust foundation for managing the Mars Rover's atmospheric temperature data collection and reporting tasks.
