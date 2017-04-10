/**
   @version 1.21 2001-08-15
   @author Cay Horstmann
*/

import java.awt.*;
import java.io.*;
import javax.imageio.*;  // Remove for SDK < 1.4 
import javax.swing.*;

/**
   A bean for viewing an image.
*/
public class ImageViewerBean extends JLabel
{

   public ImageViewerBean()
   {
      setBorder(BorderFactory.createEtchedBorder());
   }

   /**
      Sets the fileName property.
      @param fileName the image file name
   */
   public void setFileName(String fileName)
   {  
      try
      {
         file = new File(fileName);
         setIcon(new ImageIcon(ImageIO.read(file)));
      }
      catch (IOException exception)
      {
         file = null;
         setIcon(null);
      }  
   }

   // Use this version for SDK < 1.4 
   /*       
   public void setFileName(String fileName)
   {  
      file = new File(fileName);         
      Image image 
         = Toolkit.getDefaultToolkit().getImage(fileName);
      MediaTracker tracker = new MediaTracker(this);
      tracker.addImage(image, 0);
      try { tracker.waitForID(0); } catch (Exception e) {}
      setIcon(new ImageIcon(image));
      repaint();
   }
   */

   /**
      Gets the fileName property.
      @return the image file name
   */
   public String getFileName()
   {  
      if (file == null) return null;
      else return file.getPath();
   }

   public Dimension getPreferredSize()
   {
      return new Dimension(XPREFSIZE, YPREFSIZE);
   }

   private File file = null;
   private static final int XPREFSIZE = 200;
   private static final int YPREFSIZE = 200;
}






