import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Gate extends Thread {
    private final int gateNumber;
    private final List<Car> cars;
    private final AtomicInteger carsServed = new AtomicInteger(0);  // Counter for cars served by this gate

    public Gate(int gateNumber, List<Car> cars) {
        this.gateNumber = gateNumber;
        this.cars = cars;
    }

    @Override
    public void run() {
        for (Car car : cars) {
            car.start();
        }
        for (Car car : cars) {
            try {
                car.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Gate " + gateNumber + " was interrupted.");
            }
        }
    }

  
    // to increment cars served by this gate
    public void incrementCarsServed() {
        carsServed.incrementAndGet();
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public List<Car> getCars() {
        return cars;
    }

    public int getCarsServed() {
        return carsServed.get();
    }
}
