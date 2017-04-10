/**
   @version 1.01 2001-08-11
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
   This is a sample drag source for testing purposes. It consists
   of a list of files in the current directory.
*/
public class DragSourceTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new DragSourceFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
      frame.show();
   }
}

/**
   This frame contains a list of files in the current 
   directory with support for dragging files to a drop target.
   Moved files are removed from the list.
*/
class DragSourceFrame extends JFrame
{  
   public DragSourceFrame()
   {  
      setTitle("DragSourceTest");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();
      File f = new File(".").getAbsoluteFile();
      File[] files = f.listFiles();
      model = new DefaultListModel();
      for (int i = 0; i < files.length; i++)
         try
         {
            model.addElement(files[i].getCanonicalFile());
         }
         catch (IOException exception)
         {
            JOptionPane.showMessageDialog(this, exception);
         }
      fileList = new JList(model);
      contentPane.add(new JScrollPane(fileList), 
         BorderLayout.CENTER);
      contentPane.add(new JLabel("Drag files from this list"),
         BorderLayout.NORTH);

      DragSource dragSource = DragSource.getDefaultDragSource();
      dragSource.createDefaultDragGestureRecognizer(fileList,
         DnDConstants.ACTION_COPY_OR_MOVE, new
            DragGestureListener()
            {
               public void dragGestureRecognized(
                  DragGestureEvent event)
               {  
                  draggedValues = fileList.getSelectedValues();
                  Transferable transferable
                     = new FileListTransferable(draggedValues);
                  event.startDrag(null, transferable, 
                     new FileListDragSourceListener());
               }
            });
   }

   /**
      A drag source listener that removes moved
      files from the file list.
   */
   private class FileListDragSourceListener 
      extends DragSourceAdapter
   {
      public void dragDropEnd(DragSourceDropEvent event)
      {  
         if (event.getDropSuccess())
         {  
            int action = event.getDropAction();
            if (action == DnDConstants.ACTION_MOVE)
            {  
               for (int i = 0; i < draggedValues.length; i++)
                  model.removeElement(draggedValues[i]);
            }
         }
      }
   }

   private JList fileList;
   private DefaultListModel model;
   private Object[] draggedValues;
   private static final int WIDTH = 300;
   private static final int HEIGHT = 200;
}

class FileListTransferable implements Transferable
{  
   public FileListTransferable(Object[] files)
   {  
      fileList = new ArrayList(Arrays.asList(files));
   }

   public DataFlavor[] getTransferDataFlavors()
   {  
      return flavors;
   }

   public boolean isDataFlavorSupported(DataFlavor flavor)
   {  
      return Arrays.asList(flavors).contains(flavor);
   }

   public Object getTransferData(DataFlavor flavor)
      throws UnsupportedFlavorException
   {  
      if(flavor.equals(DataFlavor.javaFileListFlavor))
         return fileList;
      else if(flavor.equals(DataFlavor.stringFlavor))
         return fileList.toString();
      else
         throw new UnsupportedFlavorException(flavor);
   }

   private static DataFlavor[] flavors =
   {  
      DataFlavor.javaFileListFlavor,
      DataFlavor.stringFlavor
   };

   private java.util.List fileList;
}
