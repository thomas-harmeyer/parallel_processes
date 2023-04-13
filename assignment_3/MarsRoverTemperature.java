import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

class TemperatureReading {
  int sensorId;
  int minute;
  double temperature;

  TemperatureReading(int sensorId, int minute, double temperature) {
    this.sensorId = sensorId;
    this.minute = minute;
    this.temperature = temperature;
  }
}

class AtmosphericTemperatureModule {
  static final int NUM_SENSORS = 8;
  static final int READINGS_PER_HOUR = 60;
  private final ConcurrentLinkedQueue<TemperatureReading> readings = new ConcurrentLinkedQueue<>();
  private final ReentrantLock lock = new ReentrantLock();

  public void addReading(TemperatureReading reading) {
    readings.add(reading);
  }

  public void generateReport() {
    lock.lock();
    try {
      TemperatureReading[] top5Highest = new TemperatureReading[5];
      TemperatureReading[] top5Lowest = new TemperatureReading[5];
      double largestDifference = 0;
      int largestDifferenceStartMinute = 0;

      // Initialize top5Highest and top5Lowest arrays
      for (int i = 0; i < 5; i++) {
        top5Highest[i] = new TemperatureReading(-1, -1, Double.NEGATIVE_INFINITY);
        top5Lowest[i] = new TemperatureReading(-1, -1, Double.POSITIVE_INFINITY);
      }

      double[] minTempPerInterval = new double[6];
      double[] maxTempPerInterval = new double[6];

      for (int i = 0; i < 6; i++) {
        minTempPerInterval[i] = Double.POSITIVE_INFINITY;
        maxTempPerInterval[i] = Double.NEGATIVE_INFINITY;
      }

      while (!readings.isEmpty()) {
        TemperatureReading reading = readings.poll();
        int intervalIndex = reading.minute / 10;

        // Update top 5 highest and lowest temperatures
        for (int i = 0; i < 5; i++) {
          if (reading.temperature > top5Highest[i].temperature) {
            System.arraycopy(top5Highest, i, top5Highest, i + 1, 4 - i);
            top5Highest[i] = reading;
            break;
          }
        }
        for (int i = 0; i < 5; i++) {
          if (reading.temperature < top5Lowest[i].temperature) {
            System.arraycopy(top5Lowest, i, top5Lowest, i + 1, 4 - i);
            top5Lowest[i] = reading;
            break;
          }
        }

        // Update min and max temperatures for each interval
        if (reading.temperature < minTempPerInterval[intervalIndex]) {
          minTempPerInterval[intervalIndex] = reading.temperature;
        }
        if (reading.temperature > maxTempPerInterval[intervalIndex]) {
          maxTempPerInterval[intervalIndex] = reading.temperature;
        }
      }

      // Find the interval with the largest temperature difference
      for (int i = 0; i < 6; i++) {
        double difference = maxTempPerInterval[i] - minTempPerInterval[i];
        if (difference > largestDifference) {
          largestDifference = difference;
          largestDifferenceStartMinute = i * 10;
        }
      }

      // Print the report
      System.out.println("\nTop 5 highest temperatures:");
      for (TemperatureReading reading : top5Highest) {
        System.out.printf("Sensor %d (Minute %d): %.2fF\n", reading.sensorId,
            reading.minute, reading.temperature);
      }

      System.out.println("\nTop 5 lowest temperatures:");
      for (TemperatureReading reading : top5Lowest) {
        System.out.printf("Sensor %d (Minute %d): %.2fF\n", reading.sensorId, reading.minute, reading.temperature);
      }

      System.out.printf("\nLargest temperature difference: %.2fF (Minute %d - %d)\n",
          largestDifference, largestDifferenceStartMinute, largestDifferenceStartMinute + 9);
    } finally {
      lock.unlock();
    }
  }
}

public class MarsRoverTemperature {
  public static void main(String[] args) {
    AtmosphericTemperatureModule atmModule = new AtmosphericTemperatureModule();
    ExecutorService executor = Executors.newFixedThreadPool(AtmosphericTemperatureModule.NUM_SENSORS);
    Random random = new Random();
    CountDownLatch latch = new CountDownLatch(AtmosphericTemperatureModule.NUM_SENSORS);

    for (int i = 0; i < AtmosphericTemperatureModule.NUM_SENSORS; i++) {
      final int sensorId = i;
      executor.execute(() -> {
        for (int minute = 0; minute < AtmosphericTemperatureModule.READINGS_PER_HOUR; minute++) {
          double temperature = random.nextDouble() * 170 - 100;
          atmModule.addReading(new TemperatureReading(sensorId, minute, temperature));

          System.out.printf("Sensor %d - Minute %d: Temperature %.2fF\n", sensorId, minute, temperature);

          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        latch.countDown();
      });
    }

    try {
      latch.await();
      executor.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    atmModule.generateReport();
  }
}
