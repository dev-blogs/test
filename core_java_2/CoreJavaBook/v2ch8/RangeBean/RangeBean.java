/**
   @version 1.21 2001-08-20
   @author Cay Horstmann
*/

import java.awt.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;

/**
   A bean with two integer text fields to specify a range
   of values.
*/
public class RangeBean extends JPanel
{  
   public RangeBean()
   {  
      add(new JLabel("From"));
      add(from);
      add(new JLabel("To"));
      add(to);

      from.addVetoableChangeListener(new
         VetoableChangeListener()
         {
            public void vetoableChange(PropertyChangeEvent event)
               throws PropertyVetoException
            {  
               int v = ((Integer)event.getNewValue()).intValue();
               if (v > to.getValue())
                  throw new PropertyVetoException("from > to", 
                     event);
            }
         });

      to.addVetoableChangeListener(new
         VetoableChangeListener()
         {
            public void vetoableChange(PropertyChangeEvent event)
               throws PropertyVetoException
            {  
               int v = ((Integer)event.getNewValue()).intValue();
               if (v < from.getValue())
                  throw new PropertyVetoException("to < from", 
                     event);
            }
         });
   }

   /**
      Sets the from property.
      @param v the new lower bound of the range
   */
   public void setFrom(int v) throws PropertyVetoException
   {  
      from.setValue(v);
   }

   /**
      Gets the from property.
      @return the lower bound of the range
   */
   public int getFrom() { return from.getValue(); }

   /**
      Sets the to property.
      @param v the new upper bound of the range.
   */
   public void setTo(int v) throws PropertyVetoException
   {  
      to.setValue(v);
   }

   /**
      Gets the to property.
      @return the upper bound of the range
   */
   public int getTo() { return to.getValue(); }

   private IntTextBean from = new IntTextBean();
   private IntTextBean to = new IntTextBean();
}
