/**
   @version 1.30 2000-06-03
   @author Cay Horstmann
*/

import java.awt.*;
import javax.swing.*;

/**
   A frame with a button panel
*/
class ButtonFrame extends JFrame
{
   public ButtonFrame()
   {
      setTitle("ButtonTest");
      setSize(WIDTH, HEIGHT);

      // add panel to frame

      ButtonPanel panel = new ButtonPanel();
      Container contentPane = getContentPane();
      contentPane.add(panel);
   }

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  
}
