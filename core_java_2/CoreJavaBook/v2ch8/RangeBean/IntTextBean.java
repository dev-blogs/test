/**
   @version 1.30 2001-08-20
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

/**
   A bean for editing integer values
*/
public class IntTextBean extends JTextField 
{ 
   public IntTextBean()
   {  
      super("0", TEXTSIZE);
      setInputVerifier(new IntTextFieldVerifier());
      addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               editComplete();
            }
         });
      addFocusListener(new 
         FocusListener()
         {  
            public void focusGained(FocusEvent event)
            {  
               if (!event.isTemporary())
                  lastValue = getValue();
               
            }
            public void focusLost(FocusEvent event)
            {  
               if (!event.isTemporary())
                  editComplete();               
            }
         });
   }

   protected Document createDefaultModel() 
   {  
      return new IntTextDocument();
   }

   /**
      This method is called when the text field loses
      focus or the user hits Enter.
   */
   public void editComplete()
   {  
      Integer oldValue = new Integer(lastValue);
      Integer newValue = new Integer(getValue());
      try
      {  
         fireVetoableChange("value", oldValue, newValue);
         // survived, therefore no veto
         firePropertyChange("value", oldValue, newValue);
      }
      catch(PropertyVetoException e)
      {  
         // someone didn't like it
         JOptionPane.showMessageDialog(this, "" + e,
            "Input Error", JOptionPane.WARNING_MESSAGE);
         setText("" + lastValue);
         requestFocus();
            // doesn't work in all JDK versions--see bug #4128659
      }
   }

   /**
      Checks if the contents of this field is a valid integer.
      @return true of the field contents is valid
   */
   public boolean isValid()
   {
      return IntTextDocument.isValid(getText());
   }

   /**
      Gets the numeric value of the field contents.
      @param the number that the user typed into the field, or
      0 if the field contents is not valid.
   */
   public int getValue()
   {  
      try
      {  
         return Integer.parseInt(getText());
      }
      catch(NumberFormatException e)
      {
         return 0;  
      }
   }

   /**
      Sets the constrained value property.
      @param v the new value
   */
   public void setValue(int v) throws PropertyVetoException
   {  
      Integer oldValue = new Integer(getValue());
      Integer newValue = new Integer(v);
      fireVetoableChange("value", oldValue, newValue);
      // survived, therefore no veto
      setText("" + v);
      firePropertyChange("value", oldValue, newValue);
   }

   public Dimension getPreferredSize()
   {  
      return new Dimension(XPREFSIZE, YPREFSIZE);
   }

   private int lastValue;
   private static final int XPREFSIZE = 50;
   private static final int YPREFSIZE = 20;
   private static final int TEXTSIZE = 10;
}

/**
   A document that can only hold valid integers or their 
   substrings
*/
class IntTextDocument extends PlainDocument
{  
   public void insertString(int offs, String str, 
      AttributeSet a) 
      throws BadLocationException 
   {  
      if (str == null) return;

      String oldString = getText(0, getLength());
      String newString = oldString.substring(0, offs)
         + str + oldString.substring(offs);

      if (canBecomeValid(newString))
         super.insertString(offs, str, a);
   }

   /**
      A helper function that tests whether a string is a valid
      integer
      @param s a string
      @return true if s is a valid integer
   */
   public static boolean isValid(String s)
   {
      try
      {  
         Integer.parseInt(s);
         return true;
      }
      catch(NumberFormatException e)
      {  
         return false; 
      }
   }

   /**
      A helper function that tests whether a string is a 
      substring of a valid integer
      @param s a string
      @return true if s can be extended to a valid integer
   */
   public static boolean canBecomeValid(String s)
   {
      return s.equals("") || s.equals("-") || isValid(s);
   }
}

/**
   A verifier that checks if the contents of a text component
   is a valid integer.
*/
class IntTextFieldVerifier extends InputVerifier
{
   public boolean verify(JComponent component)
   {
      String text = ((JTextComponent)component).getText();
      return IntTextDocument.isValid(text);
   }
}
