/**
   @version 1.11 2001-08-11
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
   This program demonstrates the transfer of text
   between a Java application and the system clipboard.
*/
public class TextTransferTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new TextTransferFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
      frame.show();
   }
}

/**
   This frame has a text area and buttons for copying and 
   pasting text.
*/
class TextTransferFrame extends JFrame
{  
   public TextTransferFrame()
   {  
      setTitle("TextTransferTest");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();

      textArea = new JTextArea();
      contentPane.add(new JScrollPane(textArea), 
         BorderLayout.CENTER);
      JPanel panel = new JPanel();

      JButton copyButton = new JButton("Copy");
      panel.add(copyButton);
      copyButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               copy();
            }
         });

      JButton pasteButton = new JButton("Paste");
      panel.add(pasteButton);
      pasteButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               paste();
            }
         });

      contentPane.add(panel, BorderLayout.SOUTH);
   }

   /**
      Copies the selected text to the system clipboard.
   */
   private void copy()
   {  
      Clipboard clipboard
         = Toolkit.getDefaultToolkit().getSystemClipboard();
      String text = textArea.getSelectedText();
      if (text == null) text = textArea.getText();
      StringSelection selection = new StringSelection(text);
      clipboard.setContents(selection, null);
   }

   /**
      Pastes the text from the system clipboard into the
      text area.
   */
   private void paste()
   {  
      Clipboard clipboard
         = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable contents = clipboard.getContents(this);
      if (contents == null) return;
      DataFlavor flavor = DataFlavor.stringFlavor;
      if (contents.isDataFlavorSupported(flavor))
      {
         try
         {  
            String text 
               = (String)(contents.getTransferData(flavor));
            textArea.replaceSelection(text);
         }
         catch(UnsupportedFlavorException exception)
         {  
            JOptionPane.showMessageDialog(this, exception);
         }
         catch(IOException exception)
         {  
            JOptionPane.showMessageDialog(this, exception);
         }
      }
   }

   private JTextArea textArea;

   private static final int WIDTH = 300;
   private static final int HEIGHT = 300;
}
