import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.HashMap;

import net.tinyos.message.*;
import net.tinyos.packet.*;
import net.tinyos.util.*;

class PullMsgListener implements MessageListener
{
	GUI GUIInstance;
	String result;
	public PullMsgListener(GUI gui)
	{
		GUIInstance = gui;
	}
	public void messageReceived(int to, Message message)
	{
		System.out.println("Pull Msg received");
		PullMsg msg = (PullMsg)message;
    		if(msg.get_flag() == 1)
    		{
    			result = "Node id "+msg.get_requiredid()+" is in range of Mediator: "+msg.get_mediatorid()+". Somewhere near the image shown below.";
    			System.out.println(result);
    			GUIInstance.resultLabel.setText(result);
    			GUIInstance.imageLabel.setIcon(GUIInstance.mediator_images.get(msg.get_mediatorid()));

    		}
	}
}

public class GUI implements MessageListener,ActionListener
{
	JFrame f;
        JPanel basePanel;
        JTextField tf;
        JButton searchButton;
        JLabel label;
        public JLabel resultLabel;						// must be public as other class will use it to put result when it gets pull message reply.
	PullMsg userRequest = new PullMsg();					// create a user request packet.
 
 
	BufferedImage image;
	PullMsgListener pullListener = new PullMsgListener(this); 
 	String result;
  	HashMap<Integer,Integer> push_items = new HashMap<Integer, Integer>();					// for storing the current positions of push based items in system.
  										// key value will be the node id of which location is stored and value would be the mediator id under which it resides.
  	public HashMap<Integer, ImageIcon> mediator_images = new HashMap<Integer, ImageIcon>();				// a hash map to store image of each mediators.
  	
  	private MoteIF moteIF;		
    	ImageIcon imageIcon;							// image to help user to visialize location.
        public JLabel imageLabel;							// display image using label.

    	public GUI(MoteIF moteIF)
    	{
    		this.moteIF = moteIF;
    		this.moteIF.registerListener(new PullMsg(), pullListener);
    		this.moteIF.registerListener(new AggregationMsg(), this);
    
    		f = new JFrame("Searhing IoT");				// create a frame
    		label = new JLabel("Enter node id : ");
    		resultLabel = new JLabel();
    		basePanel = new JPanel();					// add panel
		tf = new JTextField(5);
		searchButton = new JButton("Find!");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		searchButton.addActionListener(this);
		
		imageIcon = new ImageIcon();
		imageLabel = new JLabel();
		
		resultLabel.setText("Enter the node id of the item to be searched in box, and then click on 'Find!'.");
		basePanel.add(label);
		basePanel.add(tf);
		basePanel.add(searchButton);
		basePanel.add(resultLabel);
		basePanel.add(imageLabel);
		f.add(basePanel);
		f.setSize(600,500);
		f.setLocation(100, 100);
                f.setVisible(true);
                try{
                	// prepare a hashtable which contains various images relating to mediator areas.
                	// we are storing the scaled down version of images so that at time of display it does't creates problem.
                	ImageIcon temp = new ImageIcon("images/1.JPG");
                	temp.setImage(temp.getImage().getScaledInstance(500,400,Image.SCALE_DEFAULT));
                	mediator_images.put(1, temp);
                	
                	temp = new ImageIcon("images/2.JPG");
                	temp.setImage(temp.getImage().getScaledInstance(500,400,Image.SCALE_DEFAULT));
                	mediator_images.put(2, temp);
               		
               		temp = new ImageIcon("images/3.JPG");
                	temp.setImage(temp.getImage().getScaledInstance(500,400,Image.SCALE_DEFAULT));
                	mediator_images.put(3, temp);
 		}
 		catch(Exception e)
 		{
 			System.out.println("Exception: "+e);
 		}
 	}

/*	This method will be called whenever a new message is being received by Basestaion.
	1. If it is a data aggregation packet just update the database accordingly.
	2. If it is a pull request, check weather it is a reply or not by using flag. If it is a reply packet display data directly.
*/

	public void messageReceived(int to, Message message)
  	{
/*  		if(sizeof(message) == sizeof(AggregationMsg))
  		{
  			
  */			try{
  				AggregationMsg msg = (AggregationMsg)message;				// got the message now find out the array.
    				int nodeid = msg.get_nodeid();
    				int size = msg.get_size();
    				int nodes[] = msg.get_nodes();
    				System.out.println("Aggregation Msg from node: "+nodeid+" Received");
    				for(int i = 0;i < size;i++)
    				{
    					System.out.println(nodes[i] + " is in "+nodeid+"'s range");
    					push_items.put(nodes[i],nodeid);				// update the hastable.
    				}
    			}
    			catch(Exception e)
    			{
    				System.out.println("Cant be casted to aggregation msg");
    				System.out.println(e);
    			}
    			
    /*		}
    		else if (sizeof(message) == sizeof(PullMsg))
    		{
    			PullMsg msg = (PullMsg)message;
    			if(msg.get_flag() == 1);
    			result = "Node id "+msg.get_requiredid()+" is in the region of "+msg.get_mediatorid();
    			resultLabel.setText(result);
    		}
    	*/	System.out.println("---------");
    	}
  
	private static void usage()
	{
		System.err.println("usage: TestSerial [-comm <source>]");
	}
  
/* Will be called whenever user requests for some item
   1. If it is pull based tag broadcast a user request for that item.
   2. If it is push based tag just look for database entery.
   3. Ignore and show an error message.
*/

	public void actionPerformed(ActionEvent ae)
	{
		int nodeid = Integer.parseInt(tf.getText());
		System.out.println("Node id to be searched : "+nodeid);
		if(nodeid>=11 && nodeid<=20)					// PullTag
		{
			try
			{
				userRequest.set_requiredid(nodeid);
				int flag = 0;
				userRequest.set_flag(flag);
				moteIF.send(MoteIF.TOS_BCAST_ADDR, userRequest);
				result = "Please wait...";
			}
			catch(IOException exception){
				System.err.println("Exception thrown when sending packets. Exiting.");
      				System.err.println(exception);
			}
		}
		else if(nodeid>=21 && nodeid<=30)				// PushTag
		{
			if(push_items.containsKey(nodeid))				// If basestation has information about sourght node, display it!
			{
				result = "Node id "+nodeid+" is in range of Mediator: "+push_items.get(nodeid)+". Somewhere near the image shown below.";
       				imageLabel.setIcon(mediator_images.get(push_items.get(nodeid)));
			}
			else
				result = "Node id can not be searched";
		}
		else
			result = "Something wrong happened here!";

		resultLabel.setText(result);				// display it.
	}

	public static void main(String[] args) throws Exception
	{
		PrintStream ps = new PrintStream("./output");
		//System.setOut(ps);
		//System.setErr(ps);
		
    		String source = null;
    		if (args.length == 2)
    		{
      			if (!args[0].equals("-comm"))
      			{
				usage();
				System.out.println("no arguments! exiting");
				System.exit(1);
      			}
      			source = args[1];
    		}
    		else if (args.length != 0)
    		{
      			usage();
      			System.exit(1);
    		}
    
    		PhoenixSource phoenix;
    
    		if (source == null)
    		{
      			phoenix = BuildSource.makePhoenix(PrintStreamMessenger.err);
    		}
    		else
    		{
      			phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
    		}

    		MoteIF mif = new MoteIF(phoenix);
    		GUI serial = new GUI(mif);
  	}
}
