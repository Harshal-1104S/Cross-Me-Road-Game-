import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuButtons {

	private ImageIcon charPickButton, mapPickButton, char0, char1, char2, map0, map1, map2, map3, 
	gameLogo, play, play2, newPlay, char1Down, char1Left, char1Up, char2Down, char2Left, char2Up, dialogue,
	castle, control, controlScreen;
	private int charChoice, mapChoice, charNameChoice, xPos, yPos, dir, back1Y, back2Y, score, highscore,
	castleY, obstacle, obbyY1, obbyY2, obbyY3, obbyY4, obbyY5, obbyY6, score2, waterY;
	private Random rand;
	private String charName, charName1, charName2, dial, dial1, dial2, dial3, newDial, dial4, dial5;
	private final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;
	private int obstacleArray [], waterYArray [];
	private Water wat [];
	private Rectangle2D playerMask;
	private JPanel panel;
	private static Clip bgMusic, click, talking, jump;

	public MenuButtons (JPanel pan)
	{
		//Instantiating the fields
		panel = pan;
		charPickButton = new ImageIcon ("CharSelect.png");
		mapPickButton = new ImageIcon ("MapSelect.png");
		char1 = new ImageIcon ("JMR.gif");
		char1Down = new ImageIcon ("JMD.gif");
		char1Left = new ImageIcon ("JML.gif");
		char1Up = new ImageIcon ("JMU.gif");
		char2 = new ImageIcon ("SSR.gif");
		char2Down = new ImageIcon ("SSD.gif");
		char2Left = new ImageIcon ("SSL.gif");
		char2Up = new ImageIcon ("SSU.gif");
		gameLogo = new ImageIcon ("JJ.png");
		map1 = new ImageIcon ("bg1.png");
		map2 = new ImageIcon ("bg2.png");
		map3 = new ImageIcon ("bg3.png");
		play = new ImageIcon ("playButton.png");
		play2 = new ImageIcon ("playButton.gif");
		dialogue = new ImageIcon ("DialBox.png");
		charName1 = "JumpMan";
		charName2 = "SpeedSter";
		castle = new ImageIcon ("Castle.png");
		control = new ImageIcon ("control.png");
		controlScreen = new ImageIcon ("controlscreen.png");
		rand = new Random ();
		wat = new Water [6];

		//Default play button, character name, character, map, and other settings
		char0 = char1;
		map0 = map1;
		newPlay = play;
		charName = charName1;
		charChoice = 1;
		dir = -999;
		score = 0;
		dial1 = "Click to Proceed...";
		dial2 = "Hello Adventurer."; 
		dial3 = "Venture throughout the terrain";
		dial4 = "Welcome to your Journey!";
		dial5 = "to save Prince Conway from boredom.";
		newDial = "";
		waterY = -160;
		obstacleArray = new int [6];
		waterYArray = new int [6];

	}

	//Set y method that feeds in the ypos from the main class into this class
	public void setY(int y)
	{
		yPos = y;
	}

	//Set x method that feeds in the xpos from the main class into this class
	public void setX(int x)
	{
		xPos = x;
		playerMask = new Rectangle2D.Double (xPos, 630, 100, 50);
	}

	public void setImage(int x)
	{
		charChoice = x;
	}

	public ImageIcon getImage ()
	{
		//Based on the user's choice of character, scroll through the two characters
		if (charChoice == 1)
		{
			char0 = char1;
			charName = charName1;
		}
		else if (charChoice == 2)
		{
			char0 = char2;
			charName = charName2;
		}

		return char0;
	}
	
	public ImageIcon getControl()
	{
		return control;
	}
	
	public ImageIcon getControlScreen()
	{
		return controlScreen;
	}


	public void setGameImage (int move)
	{
		//JumpMan
		if (charChoice == 1)
		{
			if (UP == move)
			{
				char0 = char1Up;
			}
			else if (DOWN == move)
			{
				char0 = char1Down;
			}
			else if (RIGHT == move)
			{
				char0 = char1;
			}
			else if (LEFT == move)
			{
				char0 = char1Left;
			}
		}
		//SpeedSter
		else if (charChoice == 2)
		{
			if (UP == move)
			{
				char0 = char2Up;
			}
			else if (DOWN == move)
			{
				char0 = char2Down;
			}
			else if (RIGHT == move)
			{
				char0 = char2;
			}
			else if (LEFT == move)
			{
				char0 = char2Left;
			}
		}
	}

	public ImageIcon getGameImage ()
	{
		return char0;
	}

	public void setBack(int y)
	{
		mapChoice = y;
	}

	public ImageIcon getBack ()
	{
		//Based on the user's choice of map, scroll through the three maps
		if (mapChoice == 1)
		{
			map0 = map1;
		}
		else if (mapChoice == 2)
		{
			map0 = map2;
		}
		else if (mapChoice == 3)
		{
			map0 = map3;
		}

		return map0;
	}

	public ImageIcon charPick ()
	{
		return charPickButton;
	}

	public ImageIcon mapPick ()
	{
		return mapPickButton;
	}

	public ImageIcon logo ()
	{
		return gameLogo;
	}

	public void setPlay (boolean z)
	{
		if (z == true)
		{
			newPlay = play2;
		}
		else if (z == false)
		{
			newPlay = play;
		}
	}

	public ImageIcon playButton ()
	{
		return newPlay;
	}

	public String getName ()
	{
		return charName;
	}

	//Feeds the direction from the main class and sets it here
	public void setDirection (int y)
	{
		if (y == LEFT) 
		{
			dir = LEFT;
		}
		if (y == RIGHT)
		{
			dir = RIGHT;
		}
		if (y == UP) 
		{
			dir = UP;
		}
		if (y == DOWN)
		{
			dir = DOWN;
		}
		if (y == 0)
		{
			dir = 0;
		}
	}

	//Movement
	public void move(int side1, int side2)
	{
		//Barrier checking
		if (xPos > side1)
		{
			//Move 50 pixels up or down accordingly
			if (dir == LEFT)
			{
				xPos -= 50;
			}
		}

		if (xPos < side2)
		{
			if (dir == RIGHT)
			{
				xPos += 50;
			}
		}
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
		playerMask = new Rectangle2D.Double (xPos, 630, 100, 50);
	}

	public void setScore (int mainScore)
	{
		score = mainScore;
	}

	//Background Movement
	public int back1move (int bg1, int bgHeight)
	{
		if (dir == UP) 
		{
			//UP Scrolling
			if (bg1 >= -500)
			{
				bg1 = -bgHeight + 200;
			}
			else 
			{
				bg1 += 40;
				
			}
			score += 10;
			
			score2 = highscoreCounter();
			
//			if (score2 % 40 == 0 && score2 != 0) //Every 4 tiles moved up
//			{
//				for (int y = 0; y < obstacleArray.length; y++)
//				{
//					//obstacleArray [y] = rand.nextInt(5) + 1;
//					obstacleArray [y] = 1;
//				}
//			}
//			for (int y = 0; y < obstacleArray.length; y++)
//			{
//				//Water Obstacle
//				if (obstacleArray [y] == 1)
//				{
//					for (int i = 0; i < wat.length; i++)
//					{
//						wat [i] = new Water (waterY, 1, xPos, 630, 100, 50, panel);
//						wat [i].setDirection(UP);							
//						waterY = wat [i].moveWater();
//					}
//				}
////				//Car Obstacle
////				else if (obstacleArray [y] == 2)
////				{
////					for (int i = 0; i < wat.length; i++)
////					{
////						wat [i] = new Water (1,-160, 2);
////						wat [i].setplayerMask(xPos, 630, 100, 50);
////						//wat [i].checkCollision();
////					}
////				}
////				//Train Obstacle
////				else if (obstacleArray [y] == 3)
////				{
////					for (int i = 0; i < wat.length; i++)
////					{
////						wat [i] = new Water (1,-160, 3);
////						wat [i].setplayerMask(xPos, 630, 100, 50);
////						//wat [i].checkCollision();
////					}
////				}
////				//Normal terrain
////				else if (obstacleArray [y] == 4 || obstacleArray [y] == 5)
////				{
////					for (int i = 0; i < wat.length; i++)
////					{
////						wat [i] = new Water (-160, 4, xPos, 630, 100, 50, panel);
////						wat [i].setDirection(UP);						
////					}
////				}
//			}
			//Spawn a new obstacle every 40 points in score
	        if (score2 % 40 == 0 && score2 != 0) {
	            for (int i = 0; i < wat.length; i++) 
	            {
	                wat[i] = new Water(-160, 1, xPos, 630, 100, 50, panel);	
	            }
	        }

	        //Move all existing obstacles
	        for (int i = 0; i < wat.length; i++) {
	            if (wat[i] != null) 
	            {
	            	wat[i].setDirection(UP); //Set direction
	                wat[i].moveWater(); //Move the obstacle
	                if (wat[i].getWaterY() >= 800) //If it exceeds the bounds, reset it to the top (off)
	            	{
	            		wat[i].setWaterY(-160);
	            	}
                	waterYArray[i] = wat[i].getWaterY(); //Set this class' waterY to match the other class'
                	wat[i].setWaterY(waterYArray[i]); //Set it so that it doesn't constantly stay at -160
	            }
	        }
		}
		if (dir == DOWN)
		{
			//Down Scrolling
			if (bg1 + bgHeight - 200 <= 0)
			{
				bg1 = 0;
			}
			else 
			{
				bg1 -= 40;
			}
			score -= 10;
		}
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
		return back1Y = bg1;
	}
	
	public int preGame(int bg1, int bgHeight)
	{
		if (dir == DOWN)
		{
			//Down Scrolling
			if (bg1 + bgHeight - 200 <= 0)
			{
				bg1 = 0;
			}
			else 
			{
				bg1 -= 40;
			}
		}
		return bg1;
	}
	

	public int back2move (int bg2, int bgHeight)
	{
		if (dir == UP) 
		{
			// Scroll down and place image outside the top side of the room
			if (bg2 >= -500)
			{
				bg2 = -bgHeight + 200;
			}
			else
			{
				bg2 += 40;
			}	
		}
		if (dir == DOWN)
		{
			// Scroll down and place image outside the top side of the room
			if (bg2 + bgHeight - 200 <= 0)
			{
				bg2 = 0;
			}
			else
			{
				bg2 -= 40;
			}	
		}
		return back2Y = bg2;
	}


	//Returns the current xPos
	public int getX()
	{
		return xPos;
	}

	//Updates the highscore
	public int highscoreCounter ()
	{
		//If the) current score is greater than the highscore, update it
		if (score > highscore)
		{
			highscore = score;
		}

		return highscore;
	}

	public ImageIcon getDial ()
	{
		return dialogue;
	}

	public String setTextDial (int text)
	{
		if (text == 1)
		{
			dial = dial1;
		}
		else if (text == 2)
		{
			dial = dial2;
		}
		else if (text == 3)
		{
			dial = dial3;
		}

		return dial;
	}

	public String setTextDial2 (int text)
	{
		if (text == 2)
		{
			newDial = dial4;
		}
		else if (text == 3)
		{
			newDial = dial5;
		}

		return newDial;
	}

	public ImageIcon getCastle ()
	{
		return castle;
	}

	public int castleMove (int y)
	{	
		castleY -= 40;
		return castleY;
	}
}
