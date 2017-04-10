/**
   @version 1.21 2001-07-17
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
   This program demonstrates a list that dynamically computes 
   list entries.
*/
public class LongListTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new LongListFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a long word list and a label that shows a 
   sentence made up from the chosen word. 
*/
class LongListFrame extends JFrame
{  
   public LongListFrame()
   {  
      setTitle("LongListTest");
      setSize(WIDTH, HEIGHT);

      wordList = new JList(new WordListModel(3));
      wordList.setSelectionMode
         (ListSelectionModel.SINGLE_SELECTION);

      wordList.setFixedCellWidth(50);
      wordList.setFixedCellHeight(15);

      JScrollPane scrollPane = new JScrollPane(wordList);

      JPanel p = new JPanel();
      p.add(scrollPane);
      wordList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent evt)
            {  
               StringBuffer word 
                  = (StringBuffer)wordList.getSelectedValue();
               setSubject(word.toString());
            }

         });

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.SOUTH);
      label = new JLabel(prefix + suffix);
      contentPane.add(label, BorderLayout.CENTER);
      setSubject("fox");
   }

   /**
      Sets the subject in the label.
      @param word the new subject that jumps over the lazy dog
   */
   public void setSubject(String word)
   {
      StringBuffer text = new StringBuffer(prefix);
      text.append(word);
      text.append(suffix);
      label.setText(text.toString());
   }

   private static final int WIDTH = 400;
   private static final int HEIGHT = 300;
   private JList wordList;
   private JLabel label;
   private String prefix = "The quick brown ";
   private String suffix = " jumps over the lazy dog.";
}

/**
   A model that dynamically generates n-letter words.
*/
class WordListModel extends AbstractListModel
{ 
   /**
      Constructs the model.
      @param n the word length
   */
   public WordListModel(int n) { length = n; }

   public int getSize()
   {  
      return (int)Math.pow(LAST - FIRST + 1, length);
   }

   public Object getElementAt(int n)
   {      
      StringBuffer r = new StringBuffer();;
      for (int i = 0; i < length; i++)
      {  
         char c = (char)(FIRST + n % (LAST - FIRST + 1));
         r.insert(0, c);
         n = n / (LAST - FIRST + 1);
      }
      return r;
   }

   private int length;
   public static final char FIRST = 'a';
   public static final char LAST = 'z';
}
