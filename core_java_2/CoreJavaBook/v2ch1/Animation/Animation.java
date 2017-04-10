/**
   @version 1.20 1999-04-23
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;  // Remove for SDK < 1.4 
import javax.swing.*;

/**
   An applet that shows a rotating globe.
*/
public class Animation extends JApplet
{  
   public void init()
   {  
      addMouseListener(new MouseAdapter()
         {  
            public void mousePressed(MouseEvent evt)
            {  
               if (runner == null)
                  start();
               else
                  stop();
            }
         });

      try
      {  
         String imageName = getParameter("imagename");
         imageCount = 1;
         String param = getParameter("imagecount");
         if (param != null)
            imageCount = Integer.parseInt(param);
         current = 0;
         image = null;
         loadImage(new URL(getDocumentBase(), imageName));
      }
      catch (Exception e)
      {  
         showStatus("Error: " + e);
      }
   }

   /**
      Loads an image.
      @param url the URL of the image file
   */
   public void loadImage(URL url)
      throws InterruptedException
         // thrown by MediaTracker.waitFor
   {  
      image = getImage(url);
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(image, 0);
      tracker.waitForID(0);
      imageWidth = image.getWidth(null);
      imageHeight = image.getHeight(null);
      resize(imageWidth, imageHeight / imageCount);
   }

   public void paint(Graphics g)
   {  
      if (image == null) return;
      g.drawImage(image, 0, - (imageHeight / imageCount)
         * current, null);
   }

   public void start()
   {  
      runner = new 
         Thread()
         {
            public void run()
            {  
               try
               {  
                  while (!Thread.interrupted())
                  {  
                     repaint();
                     current = (current + 1) % imageCount;
                     Thread.sleep(200);
                  }
               }
               catch(InterruptedException e) {}
            }
         };
      runner.start();
      showStatus("Click to stop");
   }

   public void stop()
   {  
      runner.interrupt();
      runner = null;
      showStatus("Click to restart");
   }

   private Image image;
   private int current;
   private int imageCount;
   private int imageWidth;
   private int imageHeight;
   private Thread runner;
}
