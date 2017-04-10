/**
   @version 1.20 1999-07-07
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
   This program demonstrates how to customize a "Hello, World"
   program with a properties file.
*/
public class CustomWorld
{  
   public static void main(String[] args)
   {  
      CustomWorldFrame frame = new CustomWorldFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame displays a message. The frame size, message text,
   font, and color are set in a properties file.
*/
class CustomWorldFrame extends JFrame
{  
   public CustomWorldFrame()
   {  
      Properties defaultSettings = new Properties();
      defaultSettings.put("FONT", "Monospaced");
      defaultSettings.put("SIZE", "300 200");
      defaultSettings.put("MESSAGE", "Hello, World");
      defaultSettings.put("COLOR", "0 50 50");
      defaultSettings.put("PTSIZE", "12");

      Properties settings = new Properties(defaultSettings);
      try
      {  
         FileInputStream sf
            = new FileInputStream("CustomWorld.ini");
         settings.load(sf);
      }
      catch (FileNotFoundException e) {}
      catch (IOException e) {}

      StringTokenizer st = new StringTokenizer
         (settings.getProperty("COLOR"));
      int red = Integer.parseInt(st.nextToken());
      int green = Integer.parseInt(st.nextToken());
      int blue = Integer.parseInt(st.nextToken());

      Color foreground = new Color(red, green, blue);

      String name = settings.getProperty("FONT");
      int size = Integer.parseInt(settings.getProperty("PTSIZE"));
      Font f = new Font(name, Font.BOLD, size);

      st = new StringTokenizer(settings.getProperty("SIZE"));
      int hsize = Integer.parseInt(st.nextToken());
      int vsize = Integer.parseInt(st.nextToken());
      setSize(hsize, vsize);
      setTitle(settings.getProperty("MESSAGE"));


      JLabel label = new JLabel(settings.getProperty("MESSAGE"),
         SwingConstants.CENTER);
      label.setFont(f);
      label.setForeground(foreground);
      getContentPane().add(label);
   }
}

