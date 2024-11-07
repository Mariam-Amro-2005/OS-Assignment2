import java.util.List;

public class Gate extends Thread {
    private final int gateNumber;
    private List<Car> cars;  // List of cars assigned to this gate

    public Gate(int gateNumber, List<Car> cars) {
        this.gateNumber = gateNumber;
        this.cars = cars;
    }

    // Method to set the list of cars (for use after creating the gate)
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getGateNumber() {
        return gateNumber;
    }

    @Override
    public void run() {

    }
}
