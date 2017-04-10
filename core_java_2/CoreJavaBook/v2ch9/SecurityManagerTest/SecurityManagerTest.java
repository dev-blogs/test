/**
   @version 1.11 2001-08-26
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
   This class demonstrates the use of a custom security manager
   that prohibits the reading of text files containing bad words.
*/
public class SecurityManagerTest
{  
   public static void main(String[] args)
   {  
      System.setSecurityManager(new WordCheckSecurityManager());
      JFrame frame = new SecurityManagerFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a text field to enter a file name and
   a text area to show the contents of the loaded file.
*/
class SecurityManagerFrame extends JFrame
{  
   public SecurityManagerFrame()
   {  
      setTitle("SecurityManagerTest");
      setSize(WIDTH, HEIGHT);

      fileNameField = new JTextField(20);
      JPanel panel = new JPanel();
      panel.add(new JLabel("Text file:"));
      panel.add(fileNameField);
      JButton openButton = new JButton("Open");
      panel.add(openButton);
      openButton.addActionListener(new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               loadFile(fileNameField.getText());
            }
         });

      Container contentPane = getContentPane();
      contentPane.add(panel, "North");

      fileText = new JTextArea();
      contentPane.add(new JScrollPane(fileText), "Center");
   }

   /**
      Attempt to load a file into the text area. If a security
      exception is caught, a message is inserted into the text
      area instead.
      @param filename the file name
   */
   public void loadFile(String filename)
   {  
      try
      {  
         fileText.setText("");
         BufferedReader in
            = new BufferedReader(new FileReader(filename));
         String s;
         while ((s = in.readLine()) != null)
         fileText.append(s + "\n");
         in.close();
      }
      catch (IOException e)
      {  
         fileText.append(e + "\n");
      }
      catch (SecurityException e)
      {  
         fileText.append("I am sorry, but I cannot do that.");
      }
   }

   private JTextField fileNameField;
   private JTextArea fileText;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 300;
}

