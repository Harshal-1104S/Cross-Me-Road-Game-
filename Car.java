import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Car extends JPanel implements ActionListener, KeyListener{
private Random random = new Random();

    // Car variables
    private int[] carX = new int[10];
    private int[] carY = new int[10];
    private int[] carWidth = new int[10];
    private int[] carHeight = new int[10];
    private boolean[] isMovingLeftToRight = new boolean[10];
    private int carCount = 0;
    private int[] carSpeed = new int[10]; // Array to store speed for each car

    // Lane variables
    private int[] laneY;
    private boolean[] laneDirection; // true = left to right, false = right to left
    private boolean[] laneOccupied;

    // Constants for lane spacing
    private static final int MIN_LANE_SPACING = 100;
    private static final int MAX_LANE_SPACING = 150;

    // Vehicle types
    private boolean[] isRedCar = new boolean[10]; // true = red car, false = blue bus
    private ImageIcon carRedLR, carRedRL, busBlueLR, busBlueRL;
    private ImageIcon laneImage;  // Image for the lane

    // Fixed size for cars
    private static final int FIXED_CAR_WIDTH = 50;
    private static final int FIXED_CAR_HEIGHT = 30;

    public Car() {
        setFocusable(true);
        addKeyListener(this);

        // Initialize JFrame
        JFrame frame = new JFrame("Car Game");
        frame.setContentPane(this);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load images
        carRedLR = new ImageIcon("car(red)LR.png");
        carRedRL = new ImageIcon("car(red)RL.png");
        busBlueLR = new ImageIcon("bus(blue)LR.png");
        busBlueRL = new ImageIcon("bus(blue)RL.png");
        laneImage = new ImageIcon("car lane.png"); // Load lane image

        // Initialize lanes after the frame is visible
        initializeLanes();

        // Start game timer
        frame.setVisible(true);
    }

    private void initializeLanes() {
        int totalLanes = random.nextInt(3) + 1; // Between 1 and 3 lanes
        int totalHeight = getHeight() - 40;  // Subtract ground space

        // Ensure lane spacing is within bounds
        int laneSpacing = Math.max(MIN_LANE_SPACING, Math.min(MAX_LANE_SPACING, totalHeight / (totalLanes + 1)));

        laneY = new int[totalLanes];
        laneDirection = new boolean[totalLanes];
        laneOccupied = new boolean[totalLanes];

        for (int i = 0; i < totalLanes; i++) {
            laneY[i] = 100 + (i * laneSpacing);
            laneDirection[i] = random.nextBoolean(); // Random direction
            laneOccupied[i] = false; // Initially unoccupied
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < carCount; i++) {
            if (carX[i] != -1) {
                moveCar(i);
            }
        }

        checkCollisions();
        spawnCars();
        repaint();
    }
    
    private void moveCar(int i) {
        int speed = carSpeed[i]; // Use the car's specific speed
        if (isMovingLeftToRight[i]) {
            carX[i] += speed;
            if (carX[i] > getWidth()) {
                // Car has gone off-screen to the right, respawn at the left side
                System.out.println("Car " + i + " went off-screen to the right. Respawning...");
                laneOccupied[getLaneIndex(carY[i])] = false; // Release lane
                respawnCar(i);
            }
        } else {
            carX[i] -= speed;
            if (carX[i] < -FIXED_CAR_WIDTH) {
                // Car has gone off-screen to the left, respawn at the right side
                System.out.println("Car " + i + " went off-screen to the left. Respawning...");
                laneOccupied[getLaneIndex(carY[i])] = false; // Release lane
                respawnCar(i);
            }
        }
    }

    private void respawnCar(int i) {
        // Find a valid lane that is not occupied
        int laneIndex = random.nextInt(laneY.length);

        // Ensure we do not use an occupied lane
        for (int attempts = 0; attempts < laneY.length; attempts++) {
            if (!laneOccupied[laneIndex]) {
                break;
            }
            laneIndex = (laneIndex + 1) % laneY.length;
        }

        if (laneOccupied[laneIndex]) {
            System.err.println("No available lanes to respawn car " + i);
            carX[i] = -1; // Mark car as inactive
            return; // Abort respawn if no lanes are available
        }

        carY[i] = laneY[laneIndex];
        isMovingLeftToRight[i] = laneDirection[laneIndex];
        laneOccupied[laneIndex] = true;

        // Set fixed car size
        carWidth[i] = FIXED_CAR_WIDTH;
        carHeight[i] = FIXED_CAR_HEIGHT;
        isRedCar[i] = random.nextBoolean();

        // Respawn car at the left or right side depending on the direction
        if (isMovingLeftToRight[i]) {
            carX[i] = -FIXED_CAR_WIDTH;
            System.out.println("Respawning car " + i + " at left side of lane " + laneIndex);
        } else {
            carX[i] = getWidth();
            System.out.println("Respawning car " + i + " at right side of lane " + laneIndex);
        }

        // Assign a random speed for the car between 5 and 15
        carSpeed[i] = random.nextInt(11) + 5; // Speed will be between 5 and 15
    }

    private int getLaneIndex(int y) {
        for (int i = 0; i < laneY.length; i++) {
            if (laneY[i] == y) {
                return i;
            }
        }
        return -1;
    }

    private void spawnCars() {
        // Spawn a new car if there is room
        if (carCount < carX.length) {
            int laneIndex = random.nextInt(laneY.length);

            // Ensure the selected lane is not occupied
            for (int attempts = 0; attempts < laneY.length; attempts++) {
                if (!laneOccupied[laneIndex]) {
                    break;
                }
                laneIndex = (laneIndex + 1) % laneY.length;
            }

            if (laneOccupied[laneIndex]) {
                return; // Abort spawn if no lanes are available
            }

            carY[carCount] = laneY[laneIndex];
            carWidth[carCount] = FIXED_CAR_WIDTH; // Set fixed width
            carHeight[carCount] = FIXED_CAR_HEIGHT; // Set fixed height
            isRedCar[carCount] = random.nextBoolean();
            isMovingLeftToRight[carCount] = laneDirection[laneIndex];

            // Assign initial position and speed
            if (isMovingLeftToRight[carCount]) {
                carX[carCount] = -FIXED_CAR_WIDTH;
            } else {
                carX[carCount] = getWidth();
            }

            carSpeed[carCount] = random.nextInt(11) + 5; // Speed between 5 and 15

            laneOccupied[laneIndex] = true;
            carCount++;
        }
    }

    private void checkCollisions() {
        // Check for collisions between cars
        for (int i = 0; i < carCount; i++) {
            for (int j = i + 1; j < carCount; j++) {
                if (carX[i] != -1 && carX[j] != -1) {
                    Rectangle car1 = new Rectangle(carX[i], carY[i], carWidth[i], carHeight[i]);
                    Rectangle car2 = new Rectangle(carX[j], carY[j], carWidth[j], carHeight[j]);

                    if (car1.intersects(car2)) {
                        if (carX[i] > carX[j]) {
                            carX[i] -= 1;
                        } else {
                            carX[j] -= 1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
