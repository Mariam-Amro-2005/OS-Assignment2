public class Car extends Thread {
    private final int carId;
    private final int arrivalTime;
    private final int parkingDuration;
    private final Gate gate;

    public Car(int carId, int arrivalTime, int parkingDuration, Gate gate) {
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.parkingDuration = parkingDuration;
        this.gate = gate;
    }

    @Override
    public void run() {

    }

    @Override
    public String toString() {
        return "Car " + carId + " from Gate " + gate.getGateNumber();
    }

    public int getParkingDuration() {
        return parkingDuration;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}
