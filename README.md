# parallel_processes

- Compile the program using the command "javac PrimeFinder.java"
- Run the program using the command "java PrimeFinder"
- The program will output the results to a file named "primes.txt" in the format: <execution time> <total number of primes found> <sum of all primes found> <top ten maximum primes, listed in order from lowest to highest>

Explanation:
The Sieve of Eratosthenes algorithm is a well-known and efficient method for finding all prime numbers within a given range. By dividing the range into 8 equal parts and assigning each part to a separate thread, the program will be able to take advantage of the parallel machine's 8 concurrent threads and reduce the overall execution time.

Correctness:
The Sieve of Eratosthenes algorithm is a well-established and proven method for finding prime numbers, and by dividing the range into 8 equal parts and assigning each part to a separate thread, the program will be able to find all prime numbers within the given range.

Efficiency:
By using the Sieve of Eratosthenes algorithm and taking advantage of the parallel machine's 8 concurrent threads, the program will be able to find all prime numbers within the given range in a relatively short amount of time.

Experimental Evaluation:
I tested this code with 1 thread and also by dividing up the ranges differently. This is what landed me the best results.
