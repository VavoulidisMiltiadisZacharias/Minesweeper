import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Button extends JButton 
{
	//Variables declaration
	boolean hasBomb, isDisabled ;
	int id;
	int numberOfBombsAroundIt;
	
//SETTERS - GETTERS
	public void setHasBomb(boolean hasBomb)
	{
		this.hasBomb = hasBomb; 
	}
	
	public void setIsDisabled(boolean isDisabled)
	{
		this.isDisabled = isDisabled;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setNumberOfBombsAroundIt(int numberOfBombsAroundIt)
	{
		this.numberOfBombsAroundIt = numberOfBombsAroundIt;
	}
	
	public boolean getHasBomb()
	{
		return this.hasBomb;
	}
	
	public boolean getIsDisabled()
	{
		return this.isDisabled;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getNumberOfBombsAroundIt()
	{
		return this.numberOfBombsAroundIt;
	}
	
//CONSTRUCTOR	
	Button()
	{
		this.setFocusable(false);
	}
}
