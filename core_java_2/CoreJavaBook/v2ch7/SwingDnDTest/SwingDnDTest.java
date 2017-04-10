/**
   @version 1.00 2001-08-11
   @author Cay Horstmann
*/

import java.awt.*;
import javax.swing.*;

/**
   This program demonstrates how to easily add data transfer
   capabilities to Swing components. Drag a color from the 
   "Preview" panel of the color chooser into the text field.
*/
public class SwingDnDTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new SwingDnDFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a color chooser and a text field. Dragging
   a color into the text field changes its background color.
*/
class SwingDnDFrame extends JFrame
{  
   public SwingDnDFrame()
   {  
      setTitle("SwingDnDTest");

      Container contentPane = getContentPane();
      JColorChooser chooser = new JColorChooser();
      chooser.setDragEnabled(true);
      contentPane.add(chooser, BorderLayout.CENTER);
      JTextField textField = new JTextField("Drag color here");
      textField.setDragEnabled(true);
      textField.setTransferHandler(
         new TransferHandler("background"));
      contentPane.add(textField, BorderLayout.SOUTH);
      pack();
   }
}




