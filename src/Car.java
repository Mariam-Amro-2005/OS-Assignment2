import java.util.concurrent.TimeUnit;
public class Car extends Thread {

    private  int carId;
    private  int arrivalTime;
    private  int parkingDuration;
    private  int gateNumber;
    private  Gate gate;

    public Car(int carId, int arrivalTime, int parkingDuration, int gateNumber, Gate gate) {
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
        this.gateNumber = gateNumber;
        this.gate = gate;
    }

    @Override
    public void run() {
        try {
            // Simulate arrival time
            TimeUnit.SECONDS.sleep(arrivalTime);

            // Attempt to enter parking
            ParkingLot.enter(this, gate);  // Pass the gate instance here

            // Simulate parking duration
            TimeUnit.SECONDS.sleep(parkingDuration);

            // Exit the parking lot
            ParkingLot.exit(this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Car " + carId + " from Gate " + gateNumber + " was interrupted.");
        }
    }

    // Getter methods
    public int getCarId() {
        return carId;
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getParkingDuration() {
        return parkingDuration;
    }
}
