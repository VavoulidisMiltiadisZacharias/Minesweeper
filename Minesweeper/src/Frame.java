import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

//(WRONG last column..must be 9,19,29 which means mod9==0 is wrong..check all the cases again)

//JFrame class to display everything
public class Frame extends JFrame implements ActionListener 
{

	public int counter = 1; //Counts the bombs (will be deleted later...just a helping variable for output)
	//Variables Declaration
	private JPanel centrePanel = new JPanel(); //A JPanel
	private Button[] myButton;
	private Image Bomb = new ImageIcon("Bomb.png").getImage();
	private Image One = new ImageIcon("BlackNumber1.png").getImage();
	private Image Two = new ImageIcon("BlackNumber2.png").getImage();
	private Image Three = new ImageIcon("BlackNumber3.png").getImage();
	private Image Four = new ImageIcon("BlackNumber4.png").getImage();
	private Image Five = new ImageIcon("BlackNumber5.png").getImage();
	private Image Six = new ImageIcon("BlackNumber6.png").getImage();
	private Image Seven = new ImageIcon("BlackNumber7.png").getImage();
	private Image Eight = new ImageIcon("BlackNumber8.png").getImage();
	private int maxBombs, minBombs, numberOfBombs, numberOfTiles, numberOfTilesThatDoNotHaveBomb, grayedOutTiles;
	private int rowSize, columnSize;
	
	
//Set - Get
	public void setMaxBombs(int maxBombs)
	{
		this.maxBombs = maxBombs;
	}
	
	public void setMinBombs(int minBombs)
	{
		this.minBombs = minBombs;
	}
	
	public void setNumberOfBombs(int numberOfBombs)
	{
		this.numberOfBombs = numberOfBombs;
	}
	
	public void setNumberOfTiles(int numberOfTiles)
	{
		this.numberOfTiles = numberOfTiles;
	}
	
	public void setNumberOfTilesThatDoNotHaveBomb(int numberOfTilesThatDoNotHaveBomb)
	{
		this.numberOfTilesThatDoNotHaveBomb = numberOfTilesThatDoNotHaveBomb;
	}
	
	public void setGrayedOutTiles(int grayedOutTiles)
	{
		this.grayedOutTiles = grayedOutTiles;
	}
	
	public void setRowSize(int rowSize)
	{
		this.rowSize = rowSize;
	}
	
	public void setColumnSize(int columnSize)
	{
		this.columnSize = columnSize;
	}
	
	public int getMaxBombs()
	{
		return this.maxBombs;
	}
	
	public int getMinBombs()
	{
		return this.minBombs;
	}
	
	public int getNumberOfBombs()
	{
		return this.numberOfBombs;
	}
	
	public int getNumberOfTiles()
	{
		return this.numberOfTiles;
	}
	
	public int getNumberOfTilesThatDoNotHaveBomb() 
	{
		return this.numberOfTilesThatDoNotHaveBomb;
	}
	
	public int getGrayedOutTiles()
	{
		return this.grayedOutTiles;
	}
	
	public int getRowSize()
	{
		return this.rowSize;
	}
	
	public int getColumnSize()
	{
		return this.columnSize;
	}
	
//Constructor
	Frame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize(500, 500);
		this.setTitle("Minesweeper");
		this.setNumberOfTiles(100); //100 tiles (10x10 board)
		myButton = new Button[this.getNumberOfTiles()]; //JButtons to display the tiles of the board
		
		centrePanel.setSize(500, 500);
		centrePanel.setBackground(Color.black);
		this.setRowSize((int)Math.sqrt(this.getNumberOfTiles())); //Set the size of the rows
		this.setColumnSize((int)Math.sqrt(this.getNumberOfTiles())); //Set the size of the columns
		centrePanel.setLayout(new GridLayout(this.getRowSize(),this.getColumnSize())); //Parameters must be integers
		
		//Creates JButtons in order to add them within the frame
		for(int i = 0 ; i < this.getNumberOfTiles() ; i++)
		{
			myButton[i] = new Button(); //Instantiate every JButton
			myButton[i].setId(i); //Setting the ids to the tiles
			myButton[i].setFocusable(false);
			myButton[i].setIsDisabled(false); //Initially all the button are enabled and can be clicked
			myButton[i].addActionListener(this); //Setting the buttons to perform the ActionListener interface
			myButton[i].setNumberOfBombsAroundIt(0); //Setting the bombs around all the tiles to zero initially
			centrePanel.add(myButton[i]); //Add the JButtons to the frame
		}
		
		placeBombs(myButton); //Place the bombs randomly
		
		this.setNumberOfTilesThatDoNotHaveBomb(0);
		
		//Searching for the tiles that do not have bombs
		for(int i = 0 ; i< this.getNumberOfTiles(); i++)
		{
			if(!myButton[i].getHasBomb())
				this.setNumberOfTilesThatDoNotHaveBomb(this.getNumberOfTilesThatDoNotHaveBomb()+1);
		}
		
		//Add the panel to the frame and set them visible
		this.add(centrePanel);
		this.setVisible(true);
	}
	
	
//Methods	
	//Places the bombs randomly on the board
	public void placeBombs(Button[] myButton)
	{
		this.setMaxBombs(40); //MaxBombs = 40
		this.setMinBombs(15); //MinBombs = 15
		
		this.setNumberOfBombs((int)((Math.random() * (this.getMaxBombs() - this.getMinBombs())) + this.getMinBombs())); //Defining the specific number of bombs
		int[] positionsOfBombs = new int [this.getNumberOfBombs()];
		System.out.println("Number of Bombs: " +this.getNumberOfBombs());
		
		
		//Placing the first bomb(necessary to do the first one separately)
		positionsOfBombs[0] = (int)((Math.random() * (this.getNumberOfTiles() - 1)) + 1);
		myButton[positionsOfBombs[0]].setHasBomb(true);
		//System.out.println("Position of 0 bomb: " +positionsOfBombs[0]);
		
		int bombsPlaced = 1;
		
		while(bombsPlaced < this.getNumberOfBombs())
		{
			positionsOfBombs[bombsPlaced] = (int)((Math.random() * (this.getNumberOfTiles() - 1)) + 1);
			
			//Bomb already exists on this button - Retry
			if(myButton[positionsOfBombs[bombsPlaced]].getHasBomb())
			{
				continue;
			}
				
			//Bomb successfully added
			else
			{
				myButton[positionsOfBombs[bombsPlaced]].setHasBomb(true);
				bombsPlaced++;
			}
		}
		
	/*	
		for(int i = 1 ; i < this.getNumberOfBombs() ; i ++)
		{
			int j = 0 ; //Helping variable
			positionsOfBombs[i] = (int)((Math.random() * (this.getNumberOfTiles() - 1)) + 1); //Initially place the i-th bomb
			
			while(j<i)
			{
				if(positionsOfBombs[i] == positionsOfBombs[j])
				{
					positionsOfBombs[i] = (int)((Math.random() * (this.getNumberOfTiles() - 1)) + 1);
					j = 0;
				}
				else
				{
					this.myButton[positionsOfBombs[i]].setHasBomb(true);
					j++;
				}
			}
		}	
	*/		
			this.placeNumbers(myButton);
		
			/*JUST TO KNOW WHERE DO THE BOMBS HIDE, IN ORDER TO CHECK IF THE GAME WORKS PROPERLY..DELETE LATER*/
//->		/*START*/
		/*	
			for(int i = 1 ; i < this.getNumberOfTiles() ; i++)
			{
				this.myButton[0].setHasBomb(false); //That's it purpose!!!! MUST NOT BE DELETED
				if(this.myButton[i].getHasBomb())
				{
					myButton[i].setIcon(new ImageIcon(Bomb));
					System.out.println("Position of " +counter+ " bomb: " +i);
					counter++;
				}
			}
		 */
//->		/*END*/
			
	}
	
	//Finds and returns the number of bombs around a certain (@param) button 
	public int findNumberOfBombs(Button[] myButton, Button randomButton)
	{
		//Examine all the different cases if selected button does not already contains bomb
		if(!randomButton.getHasBomb())
		{
			//Top row
			if(randomButton.getId() / 10 == 0)
			{
				//Top left button
				if(randomButton.getId() == 0)
				{
					if(myButton[randomButton.getId()+1].getHasBomb()) //Next button on the right hand size contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()+1].getHasBomb()) //Next button - below row and right hand side
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()].getHasBomb()) //Next button - below row
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					
					return randomButton.getNumberOfBombsAroundIt();
				}
				
				//Top right button
				else if(randomButton.getId() == 9)
				{
					if(myButton[randomButton.getId()-1].getHasBomb()) //Next button on the left hand size contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()-1].getHasBomb()) //Next button - below row and left hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()].getHasBomb()) //Next button - below row contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					
					return randomButton.getNumberOfBombsAroundIt();
				}
				
				//1-8 buttons
				else
				{
					if(myButton[randomButton.getId()+1].getHasBomb()) //Next button on the right hand size contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()+1].getHasBomb()) //Next button - below row and right hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()].getHasBomb()) //Next button - below row contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+this.getRowSize()-1].getHasBomb()) //Next button - below row and left hand side
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-1].getHasBomb()) //Next button on the left hand size contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					
					return randomButton.getNumberOfBombsAroundIt();
				}
			}
			
			//Bottom row
			else if(randomButton.getId() / 10 == 9)
			{
				//Bottom left button
				if(randomButton.getId() == 90)
				{
					if(myButton[randomButton.getId()-this.getRowSize()].getHasBomb()) //Next button - above row contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-this.getRowSize()+1].getHasBomb()) //Next button - above row and right hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+1].getHasBomb()) //Next button on the right hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					
					return randomButton.getNumberOfBombsAroundIt();
				}
				
				//Bottom right button
				else if(randomButton.getId() == 99)
				{
					if(myButton[randomButton.getId()-this.getRowSize()].getHasBomb()) //Next button - above row contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-1].getHasBomb()) ///Next button on the left hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-this.getRowSize()-1].getHasBomb()) //Next button - above row and left hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					
					return randomButton.getNumberOfBombsAroundIt();
				}
				
				//91-98 buttons
				else
				{
					if(myButton[randomButton.getId()-this.getRowSize()].getHasBomb()) //Next button - above row contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-this.getRowSize()+1].getHasBomb()) //Next button - above row and right hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()+1].getHasBomb()) //Next button on the right hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-1].getHasBomb()) //Next button on the left hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					if(myButton[randomButton.getId()-this.getRowSize()-1].getHasBomb()) //Next button - above row and left hand side contains bomb
						randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
					
					return randomButton.getNumberOfBombsAroundIt();
				}
			}
			
			//First column except first and last elements
			else if((randomButton.getId() % 10 == 0)
					&& (randomButton.getId() != 0)
					&& (randomButton.getId() != 90))
			{
				if(myButton[randomButton.getId()-this.getRowSize()].getHasBomb()) //Next button - above row contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()-this.getRowSize()+1].getHasBomb()) //Next button - above row and right hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+1].getHasBomb()) //Next button on the right hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()+1].getHasBomb()) //Next button - below row and right hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()].getHasBomb()) //Next button - below row contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				
				return randomButton.getNumberOfBombsAroundIt();
			}
			
			//Last column except first and last elements
			else if((randomButton.getId() % 10 == 9)
					&& (randomButton.getId() != 9)
					&& (randomButton.getId() != 99))
			{
				if(myButton[randomButton.getId()-this.getRowSize()].getHasBomb()) //Next button - above row contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()].getHasBomb()) //Next button - below row contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()-1].getHasBomb()) //Next button - below row and left hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()-1].getHasBomb()) //Next button on the left hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()-this.getRowSize()-1].getHasBomb()) //Next button - above row and left hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				
				return randomButton.getNumberOfBombsAroundIt();
			}
			
			//Inner board
			else
			{
				if(myButton[randomButton.getId()-this.getRowSize()].getHasBomb()) //Next button - above row contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()-this.getRowSize()+1].getHasBomb()) //Next button - above row and right hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+1].getHasBomb()) //Next button on the right hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()+1].getHasBomb()) //Next button - below and right hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()].getHasBomb()) //Next button - below row contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()+this.getRowSize()-1].getHasBomb()) //Next button - below row and left hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()-1].getHasBomb()) //Next button on the left hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
				if(myButton[randomButton.getId()-this.getRowSize()-1].getHasBomb()) //Next button - above row and left hand side contains bomb
					randomButton.setNumberOfBombsAroundIt(randomButton.getNumberOfBombsAroundIt()+1); //Increase bombs around it by one
			
				return randomButton.getNumberOfBombsAroundIt();
			}
		}
		
		//Button to be checked contains bomb
		else
			return 0;
	}
	
	//Places the numbers of the buttons in relation with the bombs around it 
	public void placeNumbers(Button[] myButton)
	{
		int number; //The number of the bombs around a certain tile (will be displayed on it)
		
		//Work across all the tiles and place numbers on every one 
		for(int i = 0 ; i < this.getNumberOfTiles() ; i++)
		{
			number = findNumberOfBombs(myButton, myButton[i]); //Finds the number of bombs around i-th tile
			switch(number)
			{
				case 1:
					myButton[i].setIcon(new ImageIcon(One));
					break;
				case 2:
					myButton[i].setIcon(new ImageIcon(Two));
					break;
				case 3:
					myButton[i].setIcon(new ImageIcon(Three));
					break;
				case 4:
					myButton[i].setIcon(new ImageIcon(Four));
					break;
				case 5:
					myButton[i].setIcon(new ImageIcon(Five));
					break;
				case 6:
					myButton[i].setIcon(new ImageIcon(Six));
					break;
				case 7:
					myButton[i].setIcon(new ImageIcon(Seven));
					break;
				case 8:
					myButton[i].setIcon(new ImageIcon(Eight));
					break;
				default:
					continue;
			}
		}
	}
	
	//Checks for win or lose
	public void checkWinner(Button[] myButton)
	{
		this.setGrayedOutTiles(0); //Start from zero graeyedOutTiles cause will work across all the board every time in order to check
		for(int i = 0 ; i < this.getNumberOfTiles(); i++)
		{
			if(myButton[i].getIsDisabled())
			{
				this.setGrayedOutTiles(getGrayedOutTiles()+1);
			}
			
			//Win condition
			if(this.getGrayedOutTiles() == this.getNumberOfTilesThatDoNotHaveBomb())
				System.out.println("WE HAVE A WINNER");
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		for(int i = 0 ; i < this.getNumberOfTiles() ; i++)
		{
			//If clicked button has bomb you lose
			if(e.getSource() == this.myButton[i] && this.myButton[i].getHasBomb())
			{
				myButton[i].setIcon(new ImageIcon(Bomb));
				System.out.println("Tile: " +i+" has bomb");
				System.out.println("Tile id: " +myButton[i].getId());
				System.out.println("YOU LOSE");
			}
			
			//Else clicked button is being disabled
			if(e.getSource() == this.myButton[i] && !this.myButton[i].getHasBomb()) 
			{
				myButton[i].setIsDisabled(true); //in order to be added at the disabled buttons list (My function)
				myButton[i].setEnabled(false); //Gray it out (Swing function)
				this.checkWinner(myButton);
				
				System.out.println("Tile id: " +myButton[i].getId());
			}
		}
	}
	
}
