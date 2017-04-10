/**
   @version 1.00 2002-02-05
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

/**
   A modification of the image viewer program that logs 
   various events.
*/
public class LoggingImageViewer
{
   public static void main(String[] args)
   {
      
      if (System.getProperty(
             "java.util.logging.config.class") == null
         && System.getProperty(
            "java.util.logging.config.file") == null)
      {
         try
         {
            Logger.getLogger("").setLevel(Level.ALL);
            final int LOG_ROTATION_COUNT = 10;
            Handler handler = new FileHandler(
               "%h/LoggingImageViewer.log", 
               0, LOG_ROTATION_COUNT);
            Logger.getLogger("").addHandler(handler);
         }
         catch (IOException exception)
         {
            Logger.getLogger("com.horstmann.corejava").log(
               Level.SEVERE, 
               "Can't create log file handler", exception);
         }
      }

      Handler windowHandler = new WindowHandler();
      windowHandler.setLevel(Level.ALL);
      Logger.getLogger("").addHandler(windowHandler);

      JFrame frame = new ImageViewerFrame();
      frame.setTitle("LoggingImageViewer");
      frame.setSize(300, 400);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Logger.getLogger("com.horstmann.corejava").fine(
         "Showing frame");
      frame.show();
   }
}

/**
   The frame that shows the image.
*/
class ImageViewerFrame extends JFrame
{
   public ImageViewerFrame()
   {
      logger.entering("ImageViewerFrame", "ImageViewerFrame");
      // set up menu bar
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      JMenu menu = new JMenu("File");
      menuBar.add(menu);

      JMenuItem openItem = new JMenuItem("Open");
      menu.add(openItem);
      openItem.addActionListener(new FileOpenListener());

      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               logger.fine("Exiting.");
               System.exit(0);
            }
         });

      // use a label to display the images
      label = new JLabel();
      Container contentPane = getContentPane();
      contentPane.add(label);
      logger.exiting("ImageViewerFrame", "ImageViewerFrame");
   }

   private class FileOpenListener implements ActionListener
   {
      public void actionPerformed(ActionEvent evt)
      {
         logger.entering("ImageViewerFrame.FileOpenListener", 
            "actionPerformed", evt);

         // set up file chooser
         JFileChooser chooser = new JFileChooser();
         chooser.setCurrentDirectory(new File("."));

         // accept all files ending with .gif
         chooser.setFileFilter(new
            javax.swing.filechooser.FileFilter()
            {
               public boolean accept(File f)
               {
                  return f.getName().toLowerCase()
                     .endsWith(".gif")
                     || f.isDirectory();
               }
               public String getDescription()
               {
                  return "GIF Images";
               }
            });

         // show file chooser dialog
         int r = chooser.showOpenDialog(ImageViewerFrame.this);

         // if image file accepted, set it as icon of the label
         if(r == JFileChooser.APPROVE_OPTION)
         {
            String name
               = chooser.getSelectedFile().getPath();
            logger.log(Level.FINE, "Reading file {0}", name);
            label.setIcon(new ImageIcon(name));
         }         
         else
            logger.fine("File open dialog canceled.");
         logger.exiting("ImageViewerFrame.FileOpenListener", 
            "actionPerformed");
      }
   }

   private JLabel label;
   private static Logger logger 
      = Logger.getLogger("com.horstmann.corejava");
}

/**
   A handler for displaying log records in a window.
*/
class WindowHandler extends StreamHandler
{
   public WindowHandler()
   {
      JFrame frame = new JFrame();
      final JTextArea output = new JTextArea();
      frame.setSize(200, 200);
      frame.setContentPane(new JScrollPane(output));
      frame.show();
      setOutputStream(new
         OutputStream()
         {
            public void write(int b) 
            { 
               output.append("" + (char)b); 
            }
            public void write(byte[] b, int off, int len)
            {
               output.append(new String(b, off, len));
            }
         });
   }

   public void publish(LogRecord record)
   {
      super.publish(record);
      flush();
   }
}
