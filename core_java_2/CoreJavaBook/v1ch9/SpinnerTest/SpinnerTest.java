/**
   @version 1.00 2002-03-12
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
   A program to test spinners.
*/
public class SpinnerTest
{
   public static void main(String[] args)
   {
      SpinnerFrame frame = new SpinnerFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame with a panel that contains several spinners and
   a button that displays the spinner values.
*/
class SpinnerFrame extends JFrame
{
   public SpinnerFrame()
   {
      setTitle("SpinnerTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      Container contentPane = getContentPane();
      JPanel buttonPanel = new JPanel();
      okButton = new JButton("Ok");
      buttonPanel.add(okButton);
      contentPane.add(buttonPanel, BorderLayout.SOUTH);

      mainPanel = new JPanel();
      mainPanel.setLayout(new GridLayout(0, 3));
      contentPane.add(mainPanel, BorderLayout.CENTER);

      JSpinner defaultSpinner = new JSpinner();
      addRow("Default", defaultSpinner);

      JSpinner boundedSpinner = new JSpinner(
         new SpinnerNumberModel(5, 0, 10, 0.5));
      addRow("Bounded", boundedSpinner);

      String[] fonts = GraphicsEnvironment
         .getLocalGraphicsEnvironment()
         .getAvailableFontFamilyNames();

      JSpinner listSpinner = new JSpinner(
         new SpinnerListModel(fonts));
      addRow("List", listSpinner);

      JSpinner reverseListSpinner = new JSpinner(
         new 
            SpinnerListModel(fonts)
            {
               public Object getNextValue() 
               { 
                  return super.getPreviousValue(); 
               }
               public Object getPreviousValue() 
               { 
                  return super.getNextValue(); 
               }
            });
      addRow("Reverse List", reverseListSpinner);

      JSpinner dateSpinner = new JSpinner(
         new SpinnerDateModel());
      addRow("Date", dateSpinner);

      JSpinner betterDateSpinner = new JSpinner(
         new SpinnerDateModel());
      String pattern = ((SimpleDateFormat)
         DateFormat.getDateInstance()).toPattern();
      betterDateSpinner.setEditor(new JSpinner.DateEditor(
         betterDateSpinner, pattern));
      addRow("Better Date", betterDateSpinner);

      JSpinner timeSpinner = new JSpinner(
         new SpinnerDateModel(
            new GregorianCalendar(2000, Calendar.JANUARY, 1,
               12, 0, 0).getTime(), null, null, Calendar.HOUR));
      addRow("Time", timeSpinner);
      
      JSpinner permSpinner = new JSpinner(
         new PermutationSpinnerModel("meat"));
      addRow("Word permutations", permSpinner);
   }

   /**
      Adds a row to the main panel.
      @param labelText the label of the spinner
      @param spinner the sample spinner
   */
   public void addRow(String labelText, final JSpinner spinner)
   {
      mainPanel.add(new JLabel(labelText));
      mainPanel.add(spinner);
      final JLabel valueLabel = new JLabel();
      mainPanel.add(valueLabel);
      okButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               Object value = spinner.getValue();
               valueLabel.setText(value.toString());
            }
         });
   }

   public static final int DEFAULT_WIDTH = 400;
   public static final int DEFAULT_HEIGHT = 250;  

   private JPanel mainPanel;
   private JButton okButton;
}

/**
   A model that dynamically generates word permutations
*/
class PermutationSpinnerModel extends AbstractSpinnerModel
{ 
   /**
      Constructs the model.
      @param w the word to permute
   */
   public PermutationSpinnerModel(String w) 
   {
      word = w;
   }

   public Object getValue() 
   { 
      return word; 
   }
   
   public void setValue(Object value)
   {
      if (!(value instanceof String)) 
         throw new IllegalArgumentException();
      word = (String)value;
      fireStateChanged();
   }
   
   public Object getNextValue() 
   { 
      StringBuffer buffer = new StringBuffer(word);
      for (int i = buffer.length() - 1; i > 0; i--)
      {
         if (buffer.charAt(i - 1) < buffer.charAt(i))
         {
            int j = buffer.length() - 1;
            while (buffer.charAt(i - 1) > buffer.charAt(j)) j--;
            swap(buffer, i - 1, j);
            reverse(buffer, i, buffer.length() - 1);
            return buffer.toString();
         }
      }
      reverse(buffer, 0, buffer.length() - 1);
      return buffer.toString();
   }

   public Object getPreviousValue()
   {
      StringBuffer buffer = new StringBuffer(word);
      for (int i = buffer.length() - 1; i > 0; i--)
      {
         if (buffer.charAt(i - 1) > buffer.charAt(i))
         {
            int j = buffer.length() - 1;
            while (buffer.charAt(i - 1) < buffer.charAt(j)) j--;
            swap(buffer, i - 1, j);
            reverse(buffer, i, buffer.length() - 1);
            return buffer.toString();
         }
      }
      reverse(buffer, 0, buffer.length() - 1);
      return buffer.toString();
   }

   private static void swap(StringBuffer buffer, int i, int j)
   {
      char temp = buffer.charAt(i);
      buffer.setCharAt(i, buffer.charAt(j));
      buffer.setCharAt(j, temp);
   }

   private static void reverse(StringBuffer buffer, int i, int j)
   {
      while (i < j) { swap(buffer, i, j); i++; j--; }
   }

   private String word;
}

