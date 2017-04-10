/**
   @version 1.21 2001-07-17
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   This program demonstrates a simple fixed list of strings.
*/
public class ListTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new ListFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a word list and a label that shows a 
   sentence made up from the chosen words. Note that you can 
   select multiple words with Ctrl+click and Shift+click.
*/
class ListFrame extends JFrame
{  
   public ListFrame()
   {  
      setTitle("ListTest");
      setSize(WIDTH, HEIGHT);

      String[] words =
      {  
         "quick","brown","hungry","wild","silent",
         "huge","private","abstract","static","final"
      };

      wordList = new JList(words);
      JScrollPane scrollPane = new JScrollPane(wordList);

      JPanel p = new JPanel();
      p.add(scrollPane);
      wordList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent event)
            {  
               Object[] values = wordList.getSelectedValues();

               StringBuffer text = new StringBuffer(prefix);
               for (int i = 0; i < values.length; i++)
               {  
                  String word = (String)values[i];
                  text.append(word);
                  text.append(" ");
               }
               text.append(suffix);

               label.setText(text.toString());
            }
         });

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.SOUTH);
      label = new JLabel(prefix + suffix);
      contentPane.add(label, BorderLayout.CENTER);
   }

   private static final int WIDTH = 400;
   private static final int HEIGHT = 300;
   private JList wordList;
   private JLabel label;
   private String prefix = "The ";
   private String suffix = "fox jumps over the lazy dog.";
}


