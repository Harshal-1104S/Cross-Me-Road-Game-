import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Train extends JPanel implements ActionListener {
	private int trainX = -100;
    private boolean showRedLight = true;
    private int delayCounter = 0;
    private boolean trainMoving;
    private int randomSpeed;
    private int targetX;
    private int redLightDuration;
    private int greenLightDuration;
    private Random rand;
    private boolean trainDirection; // True means left to right, false means right to left
	private Rectangle2D trainMask,playerMask;
    private ImageIcon trainLR, trainRL, roadImage, trackImage;
    private JFrame frame;

    public Train(){
    	
    	 // Create JFrame in the constructor
        frame = new JFrame("Train");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the current panel (this) as the content pane of the JFrame
        frame.setContentPane(this);  // This is where we add the Train panel to the JFrame

        // Make the frame visible
        
    roadImage = new ImageIcon("road.png");
    trackImage = new ImageIcon("train track.png");
    trainLR = new ImageIcon("trainLR.png");
    trainRL = new ImageIcon("trainRL.png");
    
    rand = new Random();
    
    trainDirection = rand.nextBoolean();
    trainMoving = false;

    }
    
    public boolean getTrainMoving()
    {
    	return trainMoving;
    }
    public boolean getTrainDirection()
    {
    	return trainDirection;
    }
    
    public int getTrainX()
    {
    	return trainX;
    }
    public ImageIcon getRoadImage()
    {
    	return roadImage;
    }
    
    public ImageIcon getTrackImage()
    {
    	return trackImage;
    }
    
    public ImageIcon getTrainRL()
    {
    	return trainRL;
    }
    
    public ImageIcon getTrainLR()
    {
    	return trainRL;
    }
    
    
    
    private void setRandomLightDurations() {
        redLightDuration = rand.nextInt(200) + 100; // Red light duration between 100ms and 300ms
        greenLightDuration = rand.nextInt(300) + 100; // Green light duration between 100ms and 400ms
    }
    
    public void actionPerformed(ActionEvent e) {
        if (showRedLight) {
            delayCounter++;
            if (delayCounter >= redLightDuration) {
                showRedLight = false;
                delayCounter = 0;
                trainMoving = true;
                randomSpeed = rand.nextInt(10) + 2; // Random speed between 2 and 6 pixels per tick
                
                // Randomize direction (true = left to right, false = right to left)
                trainDirection = rand.nextBoolean();
                
                if (trainDirection) {
                    trainX = -100;  // Start from left side if moving left to right
                    targetX = getWidth() + 100;  // Target off-screen to the right
                } else {
                    trainX = getWidth() + 100;  // Start from right side if moving right to left
                    targetX = -getWidth();  // Target off-screen to the left (adjusted to avoid early reset)
                }
            }
        } else {
            if (trainMoving) {
                if (trainDirection) {
                    trainX += randomSpeed; // Move from left to right
                } else {
                    trainX -= randomSpeed; // Move from right to left
                }

                // Check if the train has reached its target (off-screen)
                if ((trainDirection && trainX >= targetX) || (!trainDirection && trainX <= targetX)) {
                    if (trainDirection) {
                        trainX = -100; // Reset position to left side if moving left to right
                    } else {
                        trainX = getWidth() + 100; // Reset position to right side if moving right to left
                    }

                    showRedLight = true; // Switch to red light
                    trainMoving = false; // Stop train movement
                    setRandomLightDurations(); // Set new random durations for next cycle
                }
            } else {
                // The green light has ended, but the train hasn't moved yet.
                delayCounter++;
                if (delayCounter >= greenLightDuration) {
                    showRedLight = true; // Switch to red light
                    delayCounter = 0;
                    setRandomLightDurations(); // Set new random durations for next cycle
                }
            }
        }
        repaint();
    }
}
