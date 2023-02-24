## Solution

In this implementation, the Semaphore class is used to control access to the showroom. A semaphore is a synchronization tool that allows multiple threads to access a shared resource, but with a limited number of concurrent accesses. In this case, the semaphore is initialized with a value of 1, meaning that only one guest can enter the showroom at a time.

Each guest is represented by a Thread that tries to acquire the semaphore when it wants to enter the showroom. If the semaphore is not available (its value is 0), the guest will wait until it becomes available. When a guest enters the showroom, it sets the semaphore's value to 0, indicating that the showroom is busy. When the guest exits the showroom, it releases the semaphore, setting its value back to 1 and allowing the next guest to enter.

## Advantages

Ensures that only one guest is in the showroom at a time, preventing large crowds from gathering around the door and accidentally breaking the vase.
Guarantees that guests will get a chance to view the vase as long as they wait in the queue.

## Disadvantages

Guests have to wait in a queue, which may not be ideal for guests who want to view the vase right away.

## Conclusion

The third strategy, where guests are allowed to line in a queue, is a good solution for the problem as it provides a balance between the guests' needs and the Minotaur's concern for the safety of his crystal vase.
