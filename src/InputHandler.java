import java.io.*;
import java.util.*;

public class InputHandler {
    private final List<Gate> gates = new ArrayList<>();
    private final Map<Integer, Gate> gateInstances = new HashMap<>();  // Map to store a single instance of each gate

    public void loadCarsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<Integer, List<Car>> gateCarsMap = new HashMap<>();  // Map gate number to list of cars

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int gateNumber = -1;
                int carId = -1;
                int arrivalTime = -1;
                int parkingDuration = -1;

                for (String part : parts) {
                    part = part.trim();
                    if (part.startsWith("Gate")) {
                        gateNumber = Integer.parseInt(part.split(" ")[1]);
                    } else if (part.startsWith("Car")) {
                        carId = Integer.parseInt(part.split(" ")[1]);
                    } else if (part.startsWith("Arrive")) {
                        arrivalTime = Integer.parseInt(part.split(" ")[1]);
                    } else if (part.startsWith("Parks")) {
                        parkingDuration = Integer.parseInt(part.split(" ")[1]);
                    }
                }

                // Check if all values were parsed correctly
                if (gateNumber == -1 || carId == -1 || arrivalTime == -1 || parkingDuration == -1) {
                    System.out.println("Error: Invalid input format in line - " + line);
                    continue;
                }

                // Get or create the Gate instance for this gate number
                Gate gate = gateInstances.computeIfAbsent(gateNumber, this::createGate);

                // Create the Car object and add it to the gate's car list
                Car car = new Car(carId, arrivalTime, parkingDuration, gate);
                gateCarsMap.computeIfAbsent(gateNumber, k -> new ArrayList<>()).add(car);
            }

            // Create Gate objects with assigned cars and add them to the `gates` list
            for (Map.Entry<Integer, List<Car>> entry : gateCarsMap.entrySet()) {
                int gateNumber = entry.getKey();
                Gate gate = gateInstances.get(gateNumber);  // Get the existing Gate instance
                gate.setCars(entry.getValue());  // Assign cars to the Gate
                gates.add(gate);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private Gate createGate(int gateNumber) {
        return new Gate(gateNumber, new ArrayList<>());
    }


    public void startSimulation() {
        for (Gate gate : gates) {
            gate.start();
        }

        for (Gate gate : gates) {
            try {
                gate.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Pass the gates list to include gate details in the report
        ParkingLot.reportStatus(gates);
    }

    public static void main(String[] args) {
        InputHandler handler = new InputHandler();
        handler.loadCarsFromFile("src/cars_schedule.txt");  // Replace with your file path
        handler.startSimulation();
    }
}
