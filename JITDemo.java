/*
*
*	Driver for the Program
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JITDemo extends JFrame
{
	public static void main (String [] args)
	{
		LoginFrame gui = new LoginFrame(); //Call loginFrame, the rest is done on its own
		gui.setVisible(true);
	}
}