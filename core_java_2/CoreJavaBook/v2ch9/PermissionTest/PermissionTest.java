/**
   @version 1.01 2001-08-26
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.swing.*;

/**
   This class demonstrates the custom WordCheckPermission.
*/
public class PermissionTest
{  
   public static void main(String[] args)
   {  
      System.setSecurityManager(new SecurityManager());
      JFrame frame = new PermissionTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a text field for inserting words into
   a text area that is protected from "bad words". 
*/
class PermissionTestFrame extends JFrame
{  
   public PermissionTestFrame()
   {  
      setTitle("PermissionTest");
      setSize(WIDTH, HEIGHT);

      textField = new JTextField(20);
      JPanel panel = new JPanel();
      panel.add(textField);
      JButton openButton = new JButton("Insert");
      panel.add(openButton);
      openButton.addActionListener(new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               insertWords(textField.getText());
            }
         });

      Container contentPane = getContentPane();
      contentPane.add(panel, BorderLayout.NORTH);

      textArea = new WordCheckTextArea();
      contentPane.add(new JScrollPane(textArea), 
         BorderLayout.CENTER);
   }

   /**
      Tries to insert words into the text area.
      Displays a dialog if the attempt fails.
      @param words the words to insert
   */
   public void insertWords(String words)
   {  
      try
      {  
         textArea.append(words + "\n");
      }
      catch (SecurityException e)
      {  
         JOptionPane.showMessageDialog(this,
            "I am sorry, but I cannot do that.");
      }
   }

   private JTextField textField;
   private WordCheckTextArea textArea;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 300;
}

/**
   A text area whose append method makes a security check
   to see that no bad words are added.
*/
class WordCheckTextArea extends JTextArea
{  
   public void append(String text)
   {  
      WordCheckPermission p
         = new WordCheckPermission(text, "insert");
      SecurityManager manager = System.getSecurityManager();
      if (manager != null) manager.checkPermission(p);
      super.append(text);
   }
}
