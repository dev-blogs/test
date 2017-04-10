/**
   @version 1.31 2002-07-10
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class DialogTest
{
   public static void main(String[] args)
   {  
      DialogFrame frame = new DialogFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame with a menu whose File->About action shows a dialog.
*/
class DialogFrame extends JFrame 
{  
   public DialogFrame()
   {  
      setTitle("DialogTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // construct a File menu

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
      menuBar.add(fileMenu);

      // add About and Exit menu items

      // The About item shows the About dialog

      JMenuItem aboutItem = new JMenuItem("About");
      aboutItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               if (dialog == null) // first time
                  dialog = new AboutDialog(DialogFrame.this);   
               dialog.show(); // pop up dialog
            }
         });
      fileMenu.add(aboutItem);

      // The Exit item exits the program

      JMenuItem exitItem = new JMenuItem("Exit");
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });
      fileMenu.add(exitItem);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;  

   private AboutDialog dialog;
}

/**
   A sample modal dialog that displays a message and 
   waits for the user to click the Ok button.
*/
class AboutDialog extends JDialog
{  
   public AboutDialog(JFrame owner)
   {  
      super(owner, "About DialogTest", true);         
      Container contentPane = getContentPane();

      // add HTML label to center

      contentPane.add(new JLabel(
         "<HTML><H1><I>Core Java</I></H1><HR>"
         + "By Cay Horstmann and Gary Cornell</HTML>"),
         BorderLayout.CENTER);

      // Ok button closes the dialog
      
      JButton ok = new JButton("Ok");
      ok.addActionListener(new 
         ActionListener() 
         {  
            public void actionPerformed(ActionEvent evt) 
            { 
               setVisible(false); 
            } 
         });

      // add Ok button to southern border
      
      JPanel panel = new JPanel();
      panel.add(ok);
      contentPane.add(panel, BorderLayout.SOUTH);

      setSize(250, 150);
   }
}

