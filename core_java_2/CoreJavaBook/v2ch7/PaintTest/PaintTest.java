/**
   @version 1.01 2001-07-23
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

/**
   This program demonstrates the various paint modes.
*/
public class PaintTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new PaintTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains radio buttons to choose the paint mode
   and a panel that draws a circle in the selected paint mode.
*/
class PaintTestFrame extends JFrame
{  
   public PaintTestFrame()
   {  
      setTitle("PaintTest");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();
      canvas = new PaintPanel();
      contentPane.add(canvas, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();
      ButtonGroup group = new ButtonGroup();

      JRadioButton colorButton = new JRadioButton("Color", true);
      buttonPanel.add(colorButton);
      group.add(colorButton);
      colorButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               canvas.setColor();
            }
         });

      JRadioButton gradientPaintButton
         = new JRadioButton("Gradient Paint", false);
      buttonPanel.add(gradientPaintButton);
      group.add(gradientPaintButton);
      gradientPaintButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               canvas.setGradientPaint();
            }
         });

      JRadioButton texturePaintButton
         = new JRadioButton("Texture Paint", false);
      buttonPanel.add(texturePaintButton);
      group.add(texturePaintButton);
      texturePaintButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               canvas.setTexturePaint();
            }
         });

      contentPane.add(buttonPanel, BorderLayout.NORTH);
   }

   private PaintPanel canvas;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 400;
}

/**
   This panel paints a circle in various paint modes.
*/
class PaintPanel extends JPanel
{  
   public PaintPanel()
   {  
      try
      {
         bufferedImage = ImageIO.read(new File("blue-ball.gif"));
      }
      catch (IOException exception)
      {
         exception.printStackTrace();
      }
      setColor();
   }

   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.setPaint(paint);
      Ellipse2D circle
         = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
      g2.fill(circle);
   }

   /**
      Paints in a plain color.
   */
   public void setColor()
   {  
      paint = Color.red; // Color implements Paint
      repaint();
   }

   /**
      Sets the paint mode to gradient paint.
   */
   public void setGradientPaint()
   {  
      paint = new GradientPaint(0, 0, Color.red,
         (float)getWidth(), (float)getHeight(), Color.blue);
      repaint();
   }

   /**
      Sets the paint mode to texture paint.
   */
   public void setTexturePaint()
   {  
      Rectangle2D anchor = new Rectangle2D.Double(0, 0,
         2 * bufferedImage.getWidth(),
         2 * bufferedImage.getHeight());
      paint = new TexturePaint(bufferedImage, anchor);
      repaint();
   }

   private Paint paint;
   private BufferedImage bufferedImage;
}
