/**
   @version 1.10 1997-10-27
   @author Cay Horstmann
*/

import java.util.*;

/**
   An interface for being notified when a timer tick occurs.
*/
public interface TimerListener extends EventListener
{ 
   /**
      This method is called whenever the time between 
      notifications has elapsed.
      @param evt the timer event that contains
      the time of notification
   */
   public void timeElapsed(TimerEvent evt);
}

