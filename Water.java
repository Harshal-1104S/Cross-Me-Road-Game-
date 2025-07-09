import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Water  {

	private ImageIcon water, log1, log2, coin;
	private int waterX, waterY, logDirection1, log1X, log1Y, logDirection2, log2X, log2Y, speed1, speed2,
	direction, playerX, playerY, obbyType, coin1Pos, coin1X, coin1Y, coin2Pos, coin2X, coin2Y;;
	private Random rand;
	private Timer flow;
	private Rectangle2D waterMask, log1Mask, log2Mask, playerMask;
	private boolean die;
	private JPanel panel;

	public Water ()
	{
		water = new ImageIcon ("Water.gif");
		log1 = new ImageIcon ("Log.png");
		log2 = new ImageIcon ("Log.png");
		rand = new Random ();
		coin = new ImageIcon ("Coin.gif");
		//flow = new Timer (100, this);
		die = false;

		waterX = 0;

		log1Y = waterY;
		log2Y = waterY + 80;

		//Generate log speeds
		speed1 = rand.nextInt(20, 51);
		speed2 = rand.nextInt (20, 51);

		// Draw the collision masks
		waterMask = new Rectangle2D.Double(0,waterY,600, 160);
		log1Mask = new Rectangle2D.Double (log1X, log1Y, 150, 80);
		log2Mask = new Rectangle2D.Double (log1X, log1Y, 150, 80);

		//flow.start();
	}

	public Water (int yPos, int type, int x, int y, int width, int height, JPanel pan)
	{
		panel = pan;
		playerX = x;
		playerY = y;
		playerMask = new Rectangle2D.Double (x, y, width, height);
		obbyType = type;
		water = new ImageIcon ("Water.gif");
		log1 = new ImageIcon ("Log.png");
		log2 = new ImageIcon ("Log.png");
		rand = new Random ();
		//logDirection1 = rand.nextInt(2) + 1;
		//logDirection2 = rand.nextInt(2) + 1;
		logDirection1 = rand.nextInt(3) + 1;
		coin = new ImageIcon ("Coin.gif");
		//flow = new Timer (100, this);
		die = false;

		waterX = 0;
		waterY = yPos;

		if (obbyType == 1)
		{
//			//1 = Left to Right | 2 = Right to Left
//			if (logDirection1 == 1)
//			{
//				log1X = -150;
//			}
//			else if (logDirection1 == 2)
//			{
//				log1X = 750;
//			}
//			if (logDirection2 == 1)
//			{
//				log2X = -150;
//			}
//			else if (logDirection2 == 2)
//			{
//				log2X = 750;
//			}
			
			log1X = 75+(150*logDirection1);
			log2X = 75+(150*logDirection1);

			log1Y = waterY;
			log2Y = waterY + 80;

			//Generate log speeds
			speed1 = rand.nextInt(20, 51);
			speed2 = rand.nextInt (20, 51);

			// Draw the collision masks
			waterMask = new Rectangle2D.Double(0,waterY,600, 160);
			log1Mask = new Rectangle2D.Double (log1X, log1Y, 150, 80);
			log2Mask = new Rectangle2D.Double (log1X, log1Y, 150, 80);

			//flow.start();
		}

		if (obbyType == 4)
		{
			coin1Pos = rand.nextInt(5) + 1;
			coin2Pos = rand.nextInt(5) + 1;
		}

	}
	
	public void setWaterY(int y)
	{
		waterY = y;
	}
	
	public void setDirection (int dir)
	{
		direction = dir;
	}

	public void moveWater()
	{
		if (direction == 1)
		{
			waterY += 40;
			direction = 0;
			
			//System.out.println ("UP");
			//System.out.println ("MOVED TO: " + waterY + "  " + direction);
		}
		if (direction == 2)
		{
			waterY -= 40;
			direction = 0;
		}
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (obbyType == 1)
		{
			if (e.getSource() == flow)
			{
				if (logDirection1 == 1)
				{
					if (log1X >= 600)
					{
						log1X = -150;
					}
					else
					{
						log1X += speed1;	
					}
				}
				else if (logDirection1 == 2)
				{
					if (log1X <= 0)
					{
						log1X = 750;
					}
					else
					{
						log1X -= speed1;	
					}
				}
				if (logDirection2 == 1)
				{
					if (log2X >= 600)
					{
						log2X = -150;
					}
					else
					{
						log2X += speed2;	
					}
				}
				else if (logDirection2 == 2)
				{
					if (log2X <= 0)
					{
						log2X = 750;
					}
					else
					{
						log2X -= speed2;	
					}
				}

				//Move the collision masks
				log1Mask = new Rectangle2D.Double (log1X, log1Y, 150, 80);
				log2Mask = new Rectangle2D.Double (log1X, log1Y, 150, 80);
			}

			if (obbyType == 4)
			{

			}

//			panel.repaint();
			checkCollision();

		}

	}

	public ImageIcon getWater()
	{
		return water;
	}

	public ImageIcon getLog1()
	{
		return log1;
	}

	public ImageIcon getLog2()
	{
		return log2;
	}

	public int getWaterX()
	{
		return waterX;
	}

	public int getWaterY()
	{
		return waterY;
	}

	public int getLog1X()
	{
		return log1X;
	}

	public int getLog1Y()
	{
		return log1Y;
	}

	public int getLog2X()
	{
		return log2X;
	}

	public int getLog2Y()
	{
		return log2Y;
	}

	public void checkCollision()
	{
		if (playerMask != null)
		{
			if (playerMask.intersects(waterMask) && !playerMask.intersects(log1Mask) 
					&& !playerMask.intersects(log2Mask))
			{
				die = true;
			}
			if (playerMask.intersects(log1Mask))
			{
				if (logDirection1 == 1)
				{
					playerX += speed1;	
				}
				else if (logDirection1 == 2)
				{
					playerX -= speed1;	
				}
			}
			if (playerMask.intersects(log2Mask))
			{
				if (logDirection2 == 1)
				{
					playerX += speed2;	
				}
				else if (logDirection2 == 2)
				{
					playerX -= speed2;	
				}
			}
			if (playerX <= 0 || playerX + 100 >= 600)
			{
				die = true;
			}
		}
		if (die == true)
		{
			//flow.stop();
		}
	}

	public boolean died()
	{
		return die;
	}

}
