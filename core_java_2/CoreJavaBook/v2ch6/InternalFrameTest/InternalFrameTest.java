/**
   @version 1.01 2001-07-22
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   This program demonstrates the use of internal frames.
*/
public class InternalFrameTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new DesktopFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This desktop frames contains editor panes that show HTML
   documents.
*/
class DesktopFrame extends JFrame
{  
   public DesktopFrame()
   {  
      setTitle("InternalFrameTest");
      setSize(WIDTH, HEIGHT);

      desktop = new JDesktopPane();
      setContentPane(desktop);


      // set up menus

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu fileMenu = new JMenu("File");
      menuBar.add(fileMenu);
      JMenuItem openItem = new JMenuItem("Open");
      openItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               openFile();
            }
         });
      fileMenu.add(openItem);
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
      JMenu windowMenu = new JMenu("Window");
      menuBar.add(windowMenu);
      JMenuItem nextItem = new JMenuItem("Next");
      nextItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               selectNextWindow();
            }
         });
      windowMenu.add(nextItem);
      JMenuItem cascadeItem = new JMenuItem("Cascade");
      cascadeItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               cascadeWindows();
            }
         });
      windowMenu.add(cascadeItem);
      JMenuItem tileItem = new JMenuItem("Tile");
      tileItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               tileWindows();
            }
         });
      windowMenu.add(tileItem);
      final JCheckBoxMenuItem dragOutlineItem 
         = new JCheckBoxMenuItem("Drag Outline");
      dragOutlineItem.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               desktop.setDragMode(dragOutlineItem.isSelected() 
                  ? JDesktopPane.OUTLINE_DRAG_MODE
                  : JDesktopPane.LIVE_DRAG_MODE);
            }
         });
      windowMenu.add(dragOutlineItem);
   }

   /**
      Creates an internal frame on the desktop.
      @param c the component to display in the internal frame
      @param t the title of the internal frame.
   */
   public void createInternalFrame(Component c, String t)
   {  
      final JInternalFrame iframe = new JInternalFrame(t,
         true, // resizable
         true, // closable
         true, // maximizable
         true); // iconifiable

      iframe.getContentPane().add(c);
      desktop.add(iframe);

      iframe.setFrameIcon(new ImageIcon("document.gif"));

      // add listener to confirm frame closing
      iframe.addVetoableChangeListener(new
         VetoableChangeListener()
         {
            public void vetoableChange(PropertyChangeEvent event)
               throws PropertyVetoException
            {  
               String name = event.getPropertyName();
               Object value = event.getNewValue();

               // we only want to check attempts to close a frame
               if (name.equals("closed") 
                  && value.equals(Boolean.TRUE))
               {  
                  // ask user if it is ok to close
                  int result
                     = JOptionPane.showInternalConfirmDialog(
                        iframe, "OK to close?");

                  // if the user doesn't agree, veto the close
                  if (result != JOptionPane.YES_OPTION)
                     throw new PropertyVetoException(
                        "User canceled close", event);
               }
            }           
         });

      // position frame
      int width = desktop.getWidth() / 2;
      int height = desktop.getHeight() / 2;
      iframe.reshape(nextFrameX, nextFrameY, width, height);

      iframe.show();

      // select the frame--might be vetoed
      try
      {  
         iframe.setSelected(true);
      }
      catch(PropertyVetoException e)
      {}

      /* if this is the first time, compute distance between
         cascaded frames
      */

      if (frameDistance == 0)
         frameDistance = iframe.getHeight()
            - iframe.getContentPane().getHeight();

      // compute placement for next frame

      nextFrameX += frameDistance;
      nextFrameY += frameDistance;
      if (nextFrameX + width > desktop.getWidth())
         nextFrameX = 0;
      if (nextFrameY + height > desktop.getHeight())
         nextFrameY = 0;
   }

   /**
      Cascades the non-iconified internal frames of the desktop.
   */
   public void cascadeWindows()
   {  
      JInternalFrame[] frames = desktop.getAllFrames();
      int x = 0;
      int y = 0;
      int width = desktop.getWidth() / 2;
      int height = desktop.getHeight() / 2;

      for (int i = 0; i < frames.length; i++)
      {  
         if (!frames[i].isIcon())
         {  
            try
            {  
               // try to make maximized frames resizable
               // this might be vetoed
               frames[i].setMaximum(false);
               frames[i].reshape(x, y, width, height);

               x += frameDistance;
               y += frameDistance;
               // wrap around at the desktop edge
               if (x + width > desktop.getWidth()) x = 0;
               if (y + height > desktop.getHeight()) y = 0;
            }
            catch(PropertyVetoException e)
            {}
         }
      }
   }

   /**
      Tiles the non-iconified internal frames of the desktop.
   */
   public void tileWindows()
   {  
      JInternalFrame[] frames = desktop.getAllFrames();

      // count frames that aren't iconized
      int frameCount = 0;
      for (int i = 0; i < frames.length; i++)
      {  
         if (!frames[i].isIcon())
            frameCount++;
      }

      int rows = (int)Math.sqrt(frameCount);
      int cols = frameCount / rows;
      int extra = frameCount % rows;
         // number of columns with an extra row

      int width = desktop.getWidth() / cols;
      int height = desktop.getHeight() / rows;
      int r = 0;
      int c = 0;
      for (int i = 0; i < frames.length; i++)
      {  
         if (!frames[i].isIcon())
         {  
            try
            {  
               frames[i].setMaximum(false);
               frames[i].reshape(c * width,
                  r * height, width, height);
               r++;
               if (r == rows)
               {  
                  r = 0;
                  c++;
                  if (c == cols - extra)
                  {  
                     // start adding an extra row
                     rows++;
                     height = desktop.getHeight() / rows;
                  }
               }
            }
            catch(PropertyVetoException e)
            {}
         }
      }
   }

   /**
      Brings the next non-iconified internal frame to the front.
   */
   public void selectNextWindow()
   {  
      JInternalFrame[] frames = desktop.getAllFrames();
      for (int i = 0; i < frames.length; i++)
      {  
         if (frames[i].isSelected())
         {  
            // find next frame that isn't an icon and can be
            // selected
            try
            {  
               int next = (i + 1) % frames.length;
               while (next != i && frames[next].isIcon())
                  next = (next + 1) % frames.length;
               if (next == i) return;
                  // all other frames are icons or veto selection
               frames[next].setSelected(true);
               frames[next].toFront();
               return;
            }
            catch(PropertyVetoException e)
            {}
         }
      }
   }

   /**
      Asks the user to open an HTML file.
   */
   public void openFile()
   {  
      // let user select file

      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));
      chooser.setFileFilter(new 
         javax.swing.filechooser.FileFilter()
         {  
            public boolean accept(File f)
            {  
               String fname = f.getName().toLowerCase();
               return fname.endsWith(".html")
                  || fname.endsWith(".htm")
                  || f.isDirectory();
            }
            public String getDescription()
            { 
               return "HTML Files"; 
            }
         });
      int r = chooser.showOpenDialog(this);

      if (r == JFileChooser.APPROVE_OPTION)
      {  
         // open the file that the user selected

         String filename = chooser.getSelectedFile().getPath();
         try
         {  
            URL fileUrl = new URL("file:" + filename);
            createInternalFrame(createEditorPane(fileUrl),
               filename);
         }
         catch(MalformedURLException e)
         {
         }
      }
   }

   /**
      Creates an editor pane.
      @param u the URL of the HTML document
   */
   public Component createEditorPane(URL u)
   {  
      // create an editor pane that follows hyperlink clicks

      JEditorPane editorPane = new JEditorPane();
      editorPane.setEditable(false);
      editorPane.addHyperlinkListener(new 
         HyperlinkListener()
         {  
            public void hyperlinkUpdate(HyperlinkEvent event)
            {  
               if (event.getEventType()
                  == HyperlinkEvent.EventType.ACTIVATED)
                  createInternalFrame(createEditorPane(
                     event.getURL()), event.getURL().toString());
            }
         });
      try
      {  
         editorPane.setPage(u);
      }
      catch(IOException e)
      {  
         editorPane.setText("Exception: " + e);
      }
      return new JScrollPane(editorPane);
   }

   private JDesktopPane desktop;
   private int nextFrameX;
   private int nextFrameY;
   private int frameDistance;

   private static final int WIDTH = 600;
   private static final int HEIGHT = 400;
}
