import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * JumpMan Journey
 * Harshal Mehta
 */

public class CMS_GUI extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{

	//Fields
	private JFrame frame;
	//private Player player;
	private MenuButtons menu;
	private Train train;
	private int characterPick, mapPick, charX, charY, charPickX, charPickY, mapPickX, mapPickY, logoY, 
	playX, playY, level, score, stopScroll, nameCheck, yBack1, yBack2, direction, dial, dial2, castleY,
	transitionTime, transitionTime2, waterY,control;
	private boolean mapIsPicked, goodName, transitionDone, loadChar, queueDia, queueTransition, queueBg,showRedLight;
	private Timer bgScroll, askName, transition, back, transition2;
	private String name, dialogue, dialogue2;
	private Font f, f2, f3;
	private FontMetrics fm;
	private Color black, brown;
	private final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
	private Water wat [];
	private static Clip chat, bgMusic, jump;

	public static void main (String[] args) {
		new CMS_GUI();
	}

	public CMS_GUI ()
	{
		//Calls the constructor of the menubuttons (almost everything) class
		menu = new MenuButtons(this);

		wat = new Water [6];

		//Default Settings
		level = 1;
		characterPick = 1;
		mapPick = 0;
		score = 0;
		control = 0;
		charPickX = 350;
		charPickY = 650;
		mapPickX = 475;
		mapPickY = 650;
		stopScroll = 0;
		mapIsPicked = false;
		goodName = true;
		transitionDone = false;
		direction = 0;
		name = "";
		yBack1 = 0;
		yBack2 = 0;
		dial = 1;
		dial2 = 1;
		castleY = 0;
		transitionTime = 0;
		loadChar = false;
		waterY = -160;
		queueDia = false;
		queueTransition = false;
		queueBg = false;
		showRedLight = true;

		//Set the timers
		askName = new Timer (25, this);
		transition = new Timer (20, this);
		bgScroll = new Timer (3000, this);
		transition2 = new Timer (400, this);

		//Font stuff
		try 
		{
			f = Font.createFont(Font.TRUETYPE_FONT, new File
					("emulogic.ttf")).deriveFont(20f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File
					("emulogic.ttf")));
			fm = getFontMetrics(f);
		} catch (IOException | FontFormatException e) {}

		try 
		{
			f2 = Font.createFont(Font.TRUETYPE_FONT, new File
					("emulogic.ttf")).deriveFont(10f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File
					("emulogic.ttf")));
			fm = getFontMetrics(f2);
		} catch (IOException | FontFormatException e) {}

		try 
		{
			f3 = Font.createFont(Font.TRUETYPE_FONT, new File
					("emulogic.ttf")).deriveFont(15f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File
					("emulogic.ttf")));
			fm = getFontMetrics(f3);
		} catch (IOException | FontFormatException e) {}

		//Key Implementation
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		//Mouse Implementation
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		requestFocus();

		setLayout(null);

		//Frame setup
		frame = new JFrame();
		frame.setContentPane(this);
		frame.setResizable(false);
		frame.setTitle("JumpMan Journey");
		frame.setSize(600,800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true); //If this runs before the ball object was made, it will show errors

		//Position the images accordingly (character and play button)
		charX = (getWidth()/2) - 100;
		charY = (getHeight()/2) + 50;
		playX = (getWidth()/2) - 125;
		playY = (getHeight()/2) - 160;
		logoY = (frame.getHeight()/5) - 165;

		//Call the water class' constructor
		for (int i = 0; i < wat.length; i++)
		{
			wat [i] = new Water();
		}

		//Start the timers
		bgScroll.start();
		try {
			// Open an audio input stream to background music.
			File soundFile = new File("bgMusic.wav");
			AudioInputStream audioIn2 =
					AudioSystem.getAudioInputStream(soundFile);
			bgMusic = AudioSystem.getClip();
			bgMusic.open(audioIn2);
			bgMusic.start();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "File not Found");
		}
		bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (level == 1)
		{
			//If mouse clicks the character picker
			if (e.getX() >= charPickX && e.getX() <= charPickX + 100
					&& e.getY() >= charPickY && e.getY() <= charPickY + 100)
			{
				characterPick++;

				//There are 2 characters in rotation, if it exceeds this, go back to the first character
				if (characterPick > 2)
				{
					characterPick = 1;
				}

				//Call the set image method and feed in the user's choice
				menu.setImage(characterPick);

				//Repaint to reflect the user's choice
				repaint ();
			}

			//If mouse clicks the map picker
			if (e.getX() >= mapPickX && e.getX() <= mapPickX + 100
					&& e.getY() >= mapPickY && e.getY() <= mapPickY + 100)
			{
				bgScroll.stop();
				stopScroll++;

				//Sets the map picked to true
				if (mapIsPicked == false)
				{
					mapIsPicked = true;
				}

				//Only does it once
				if (stopScroll == 1)
				{
					mapPick = 0;
				}

				mapPick++;

				//There are 3 maps in rotation, if it exceeds this, go back to the first map
				if (mapPick > 3)
				{
					mapPick = 1;
				}

				//Call the set image method and feed in the user's choice
				menu.setBack(mapPick);
			}

			//If mouse clicks the play button
			if (e.getX() >= playX && e.getX() <= playX + 250
					&& e.getY() >= playY && e.getY() <= playY + 100)
			{
				if (mapIsPicked == true)
				{
					mapIsPicked = false;
					bgMusic.stop();			
					//Switch to the playing screen
					level = 2;

					//Start Timers
					askName.start();	
				}
				else
				{
					JOptionPane.showMessageDialog (null, "PICK A MAP!", "JumpMan Journey", 
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			if (e.getX() >= 50 && e.getX() <= 150
					&& e.getY() >= 650 && e.getY() <= 750)
			{
				control++;
			}

		}

		//On the dialogue level
		if (level == 2)
		{
			if (e.getX() >= 50 && e.getX() <= getWidth() - 50
					&& e.getY() >= 2*(getHeight()/3) + 100 && e.getY() <= getHeight() - 50)
			{
				dial++;
				dial2++;
			}
			if (queueTransition == false)
			{
				if (dial >= 4)
				{
					dial = 4;
					chat.stop();
					transition.start();
					queueTransition = true;
				}
			}
		}

		//Repaint to update the game image
		repaint();

	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) 
	{
		if (e.getX() >= playX && e.getX() <= playX + 250 && e.getY() >= playY && e.getY() <= playY + 100) 
		{ 
			//Image purposes
			menu.setPlay(true);
		} 
		else 
		{
			//Image purposes
			menu.setPlay(false);
		}

		//Repaint
		repaint();
	}

	public void mouseReleased(MouseEvent e) {}

	public void keyTyped(KeyEvent e) 
	{	
		if (e.getKeyChar() == KeyEvent.VK_SPACE)
		{
			menu.setDirection(direction);

			menu.move(50, 450);

			yBack1 = menu.back1move(yBack1, getHeight());
			yBack2 = menu.back2move(yBack2, getHeight());
			waterY += 40;
			if (waterY >= 800)
			{
				waterY = -160;
			}
			for (int i = 0; i < wat.length; i++) 
			{
				wat[i].setWaterY(waterY);
			}
		}
	}

	public void keyPressed(KeyEvent e) 
	{	
		//Screens where the player image does not change (direction & movement)
		if (level != 1 && level != 2 && level != 7)
		{
			//If the up W key is pressed
			if (e.getKeyCode() == KeyEvent.VK_W)
			{
				direction = UP;
				menu.setGameImage(UP);
			}

			//If the S key is pressed
			if (e.getKeyCode() == KeyEvent.VK_S)
			{
				direction = DOWN;
				menu.setGameImage(DOWN);
			}
			//If the A key is pressed
			if (e.getKeyCode() == KeyEvent.VK_A)
			{
				direction = LEFT;
				menu.setGameImage(LEFT);			
			}
			//If the D key is pressed
			if (e.getKeyCode() == KeyEvent.VK_D)
			{
				direction = RIGHT;
				menu.setGameImage(RIGHT);
			}
		}
	}


	public void keyReleased(KeyEvent e) 
	{
		if ((e.getKeyCode() == KeyEvent.VK_W && direction == UP) || 
				(e.getKeyCode() == KeyEvent.VK_S && direction == DOWN)) 
		{ 
			direction = 0;
			menu.setDirection(0);
		}
	}

	public void actionPerformed(ActionEvent e) {

		//3 second timer to load and then ask for the user's name
		if (e.getSource() == askName)
		{
			//Stop the timer
			askName.stop();

			//Remove the loading in the middle of the screen

			while (goodName == true)
			{
				nameCheck = 0;

				name = JOptionPane.showInputDialog (null, "What's your Name? (Max. 10 Letters/#'s, NO SPACES)", "JumpMan Journey", 
						JOptionPane.INFORMATION_MESSAGE);

				//If the user clicks the cancel or close button
				if (name == null || name.isEmpty())
				{
					//Change their name to "Guest"
					name = "Guest";

					//Break the loop
					goodName = false;
				}

				if (name.length() <= 10)
				{
					for (int i = 0; i < name.length(); i++)
					{
						if (Character.isLetterOrDigit(name.charAt(i)))
						{
							nameCheck++;
						}
					}

					if (nameCheck == name.length())
					{
						goodName = false;
					}	
				}

				//Tell them that the name is wrong
				if (goodName == true)
				{
					JOptionPane.showMessageDialog (null, "WRONG NAME BUCKO!", "JumpMan Journey", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (queueDia == false)
				{
					queueDia = true;
					try {
						// Open an audio input stream to dialogue music.
						File soundFile = new File("Prologue Theme.wav");
						AudioInputStream audioIn2 =
								AudioSystem.getAudioInputStream(soundFile);
						chat = AudioSystem.getClip();
						chat.open(audioIn2);
						chat.start();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "File not Found");
					}
					chat.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
		}

		if (e.getSource() == bgScroll)
		{
			mapPick++;

			//There are 3 maps in rotation, if it exceeds this, go back to the first map
			if (mapPick > 3)
			{
				mapPick = 1;
			}

			//Call the set image method and feed in the user's choice
			menu.setBack(mapPick);
		}

		if (e.getSource() == transition)
		{
			transitionTime++;

			if (transitionTime >= 50 && transitionTime < 150)
			{
				castleY = menu.castleMove(castleY);
				menu.setDirection(DOWN);
				yBack1 = menu.preGame(yBack1, getHeight());
				yBack2 = menu.preGame(yBack2, getHeight());
			}

			if (transitionTime >= 150)
			{
				loadChar = true;
				transition.stop();
				transition2.start();
				//Default positioning (y axis to be moved)
				charY = getHeight() + 20;
				menu.setY(charY);
				charX += 40;
				menu.setX(charX);
			}
		}

		if (e.getSource() == transition2)
		{
			if (charY > 420)
			{
				charY -= 40;
				try {
					// Open an audio input stream to background music.
					File soundFile = new File("SFX_Jump_07.wav");
					AudioInputStream audioIn2 =
							AudioSystem.getAudioInputStream(soundFile);
					jump = AudioSystem.getClip();
					jump.open(audioIn2);
					jump.start();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "File not Found");
				}
			}
			else if (charY <= 420)
			{
				transition.stop();
				transitionDone = true;
				menu.setScore(score);

				if (queueBg == false)
				{
					queueBg = true;
					try {
						// Open an audio input stream to background music.
						File soundFile = new File("bgMusic.wav");
						AudioInputStream audioIn2 =
								AudioSystem.getAudioInputStream(soundFile);
						bgMusic = AudioSystem.getClip();
						bgMusic.open(audioIn2);
						bgMusic.start();
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "File not Found");
					}
					bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
		}
		
		//Repaint
		repaint ();
	}


	public void paint (Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		//While on the menu screen (not the main game)
		if (level == 1)
		{	
			//Draw the map that is picked
			g2.drawImage(menu.getBack().getImage(), 0,0, getWidth(), getHeight(), this);

			//Draw the character that is picked
			g2.drawImage(menu.getImage().getImage(), charX, charY, 200, 200, this);

			//Draw the character picker button
			g2.drawImage(menu.charPick().getImage(), charPickX, charPickY, 100, 100, this);

			//Draw the map picker button
			g2.drawImage(menu.mapPick().getImage(), mapPickX, mapPickY, 100, 100, this);	

			//Draw the Game Logo
			g2.drawImage(menu.logo().getImage(), 0, logoY, frame.getWidth(), 180, this);

			//Draw the play button
			g2.drawImage(menu.playButton().getImage(), playX, playY, 250, 100, this);

			//Draw the controls button
			g2.drawImage (menu.getControl().getImage(), 50, 650, 100, 100, this);
			
			//Font color
			black = new Color (0,0,0);
			brown = new Color (150,75,0); //For later use

			//Set color and font properties
			g2.setFont (f);
			g2.setColor(black);

			//Output the number of seconds in the center of the JFrame
			g2.drawString(menu.getName(), charX, charY);
			
			//Draw the controls
			if (control % 2 == 1 && control != 0)
			{
				g2.drawImage(menu.getControlScreen().getImage(), 100, 135, 400, 530, this);
			}
		}

		//Lore and/or loading phase
		if (level == 2)
		{
			//Set the jOptionPane color
			frame.getContentPane().setBackground(black);

			if (name != "" && goodName == false) //If the name has been picked
			{	
				if (dial < 4)
				{
					//Draw the character that is picked
					g2.drawImage(menu.getImage().getImage(), charX - 135, charY + 20, 100, 100, this);

					//Draw the Dialog Box
					g2.drawImage(menu.getDial().getImage(), 0, 2*(getHeight()/3), getWidth(), getHeight()/3, this);

					//Set font properties
					g2.setFont (f2);
					g2.setColor(brown);

					//Draw the user's name
					g2.drawString(name, 50,575);

					//Set font properties
					g2.setFont (f3);

					dialogue = menu.setTextDial(dial);
					dialogue2 = menu.setTextDial2(dial2);

					//Draw the dialogue
					g2.drawString(dialogue, 34,670);

					//Draw the continued dialogue
					g2.drawString(dialogue2, 34,710);
				}

				if (dial >= 4)
				{					
					//Draw the map that is picked
					g2.drawImage(menu.getBack().getImage(), 0, yBack1 + getWidth(), getWidth(), getHeight(), this);
					g2.drawImage (menu.getBack().getImage(), 0, yBack2, getWidth(), getHeight(), this); // Scrolling bg	
					//Draw the Castle
					g2.drawImage(menu.getCastle().getImage(), 0, castleY, getWidth(), getHeight()/5, this);
					if (loadChar == true)
					{
						//Draw the character that is picked
						g2.drawImage(menu.getGameImage().getImage(), charX, charY, 100, 100, this);
					}
				}

				//Switch to the next level
				if (transitionDone == true)
				{
					menu.setX(charX);
					level = 3;
				}
			}
			
			if (control % 2 == 1 && control != 0)
			{
				g2.drawImage(menu.getControlScreen().getImage(), 100, 135, 400, 530, this);


		}

		//Main game (after loading phase)
		if (level == 3)
		{	
			//Update the score
			score = menu.highscoreCounter();

			//			if (score != 0)
			//			{
			//				for (int i = 0; i < wat.length; i++)
			//				{
			//					wat [i] = new Water(this, waterY);
			//					waterY = wat [i].getWaterY();
			//				}
			//			}

			//Draw the map that is picked
			g2.drawImage(menu.getBack().getImage(), 0, yBack1 + getWidth(), getWidth(), getHeight(), this);
			g2.drawImage (menu.getBack().getImage(), 0, yBack2, getWidth(), getHeight(), this); // Scrolling bg			

			if (score >= 40)
			{
				for (int i = 0; i < wat.length; i++) 
				{
				System.out.println ("WATER: " + wat [i].getWaterY());
				//Draw the water of the obstacle
				g2.drawImage(wat [i].getWater().getImage(), wat[i].getWaterX(), wat[i].getWaterY(), 600, 160, this);
				System.out.println (wat[i].getWaterX() + "___" + wat[0].getWaterY());
				//Draw logs of the obstacle
				g2.drawImage(wat [i].getLog1().getImage(), wat [i].getLog1X(), wat [i].getLog1Y(), 150, 80, this);
				g2.drawImage(wat [i].getLog2().getImage(), wat [i].getLog2X(), wat [i].getLog2Y(), 150, 80, this);
			}
			}

			//Draw the character that is picked
			g2.drawImage(menu.getGameImage().getImage(), menu.getX(), charY, 100, 100, this);

			//Output the name above the character
			g2.drawString(name, menu.getX() + 20, charY);

			//Set color and font properties
			g2.setFont (f);
			g2.setColor(brown);
			//Output the score
			g2.drawString("SCORE:" + score, 50, 50);
			
			// Draw background image (road)
	        g2.drawImage(train.getRoadImage().getImage(), 0, 0, getWidth(), getHeight(), this);

	        // Calculate half of the width for track and train to cover half of the road
	        int halfLaneWidth = getWidth() / 2;

	        // Draw tracks across the screen, spaced evenly
	        for (int i = 0; i < getWidth(); i += train.getTrackImage().getIconWidth()) {
	            g2.drawImage(train.getTrackImage().getImage(), i, 280, train.getTrackImage().getIconWidth(), 300, this);
	        }

	        // Set traffic light color
	        if (showRedLight) {
	            g2.setColor(Color.RED);
	        } else {
	            g2.setColor(Color.GREEN);
	        }

	        // Draw traffic light
	        g2.fillOval(550, 275, 20, 20);

	        // Draw the train (adjust size to cover half of the lane width)
	        if (train.getTrainMoving()) {
	            if (train.getTrainDirection()) {
	                g2.drawImage(train.getTrainLR().getImage(),train.getTrainX(), 350, halfLaneWidth, 175, this); // Left to right
	            } else {
	                g2.drawImage(train.getTrainRL().getImage(),train.getTrainX(), 350, halfLaneWidth, 175, this); // Right to left
	            }

	            // Draw collision mask (around the train)
	            g2.setColor(Color.RED);  // Collision mask color (red for visibility)
	            g2.setStroke(new BasicStroke(2));  // Make the mask line thicker for visibility

	            // Rectangle for collision detection (around the train)
	            if (train.getTrainDirection()) {
	                g2.drawRect(train.getTrainX(), 350, halfLaneWidth, 175);  // Left to right train
	            } else {
	                g2.drawRect(train.getTrainX(), 350, halfLaneWidth, 175);  // Right to left train
	            }
	        }

		}

		//Minigame #1
		if (level == 4)
		{

		}

		//Minigame #2
		if (level == 5)
		{

		}

		//Minigame #3
		if (level == 6)
		{

		}

		//Game lose panel
		if (level == 7)
		{

		}
	}
	}
}


