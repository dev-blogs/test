/**
   @version 1.11 2001-08-21
   @author Cay Horstmann
*/

import java.util.*;

/**
   A class to describe timer events.
*/
public class TimerEvent extends EventObject
{ 
   /**
      Constructs a timer event.
      @param source the event source
   */
   public TimerEvent(Object source)
   {  
      super(source);
      now = new Date();
   }

   /**
      Reports when the event was constructed.
      @return the construction date and time
   */
   public Date getDate() 
   { 
      return now; 
   }

   public String toString() 
   { 
      return now + ":" + super.toString(); 
   }
   
   private Date now;
}
