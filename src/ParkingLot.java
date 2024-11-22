import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

public class ParkingLot {
    private static final int MAX_SPOTS = 4;
    private static  Semaphore parkingSpots = new Semaphore(MAX_SPOTS, true);
    private static  AtomicInteger currentCars = new AtomicInteger(0);
    private static  AtomicInteger carsServed = new AtomicInteger(0);

    public static void enter(Car car, Gate gate) { 
        
        long waitToStart = System.currentTimeMillis();
        boolean acquired = parkingSpots.tryAcquire();
        if (acquired) {
            currentCars.incrementAndGet();
            carsServed.incrementAndGet();
            gate.incrementCarsServed(); 
            System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateNumber() +
                    " parked. (Parking Status: " + currentCars.get() + " spots occupied)");
        } else {
            System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateNumber() + " waiting for a spot.");
            try {
                parkingSpots.acquire();
                long waitToEnd = System.currentTimeMillis();
                long waitTime = (waitToEnd - waitToStart) / 1000; // in seconds
                currentCars.incrementAndGet();// the car is in the parking lot now
                carsServed.incrementAndGet();
                gate.incrementCarsServed();
                System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateNumber() +
                        " parked after waiting for " + waitTime + " units of time. (Parking Status: "
                        + currentCars.get() + " spots occupied)");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateNumber() +
                        " was interrupted while waiting for a spot.");
            }
        }
    }

    public static void exit(Car car) {
        currentCars.decrementAndGet();
        parkingSpots.release();
        System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateNumber() +
                " left after " + car.getParkingDuration() + " units of time. (Parking Status: " + currentCars.get()
                + " spots occupied)");
    }


    public static synchronized void reportStatus(List<Gate> gates) {
        System.out.println(".......");
        System.out.println("Total cars served: " + carsServed.get());
        System.out.println("Current Cars in Parking: " + currentCars);
        System.out.println("Details: ");
        for (Gate gate : gates) {
            System.out.println("-Gate " + gate.getGateNumber() + " served " + gate.getCarsServed() + " cars.");
        }
    }
}
