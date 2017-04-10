/**
   @version 1.11 2001-08-20
   @author Cay Horstmann
*/

import java.io.*;
import java.util.*;
import javax.swing.event.*;

/**
   A nonvisual bean that sends timer events.
*/
public class TimerBean implements Serializable
{
   public TimerBean()
   {
      listenerList = new EventListenerList();
   }

   /**
      Sets the interval property.
      @param i the interval between timer ticks (in milliseconds)
   */
   public void setInterval(int i) { interval = i; }

   /**
      Gets the interval property.
      @return the interval between timer ticks (in milliseconds)
   */
   public int getInterval() { return interval; }

   /**
      Sets the running property.
      @param b true if the timer is running
   */
   public void setRunning(boolean b)
   {  
      if (b && runner == null)
      {  
         if (interval <= 0) return;         
         runner = new 
            Thread()
            {
               public void run()
               {  
                  try
                  {  
                     while (!Thread.interrupted())
                     {  
                        Thread.sleep(interval);
                        fireTimeElapsed(new TimerEvent(this));
                     }
                  }
                  catch(InterruptedException e)
                  {
                  }
               }
            };
         runner.start();
      }
      else if (!b && runner != null)
      {  
         runner.interrupt();
         runner = null;
      }
   }

   /**
      Gets the running property.
      @return true if the timer is running
   */
   public boolean isRunning() { return runner != null; }

   /**
      Adds a timer listener.
      @param listener the listener to add
   */
   public void addTimerListener(TimerListener listener)
   {  
      listenerList.add(TimerListener.class, listener);
   }

   /**
      Removes a timer listener.
      @param listener the listener to remove
   */
   public void removeTimerListener(TimerListener listener)
   {  
      listenerList.add(TimerListener.class, listener);
   }

   /**
      Sends a timer event to all listeners.
      @param event the event to send
   */
   public void fireTimeElapsed(TimerEvent event)
   {  
      EventListener[] listeners = listenerList.getListeners(
         TimerListener.class);

      for (int i = 0; i < listeners.length; i++)
      {  
         TimerListener listener = (TimerListener)listeners[i];
         listener.timeElapsed(event);
      }
   }

   private int interval = 1000;
   private EventListenerList listenerList;
   private Thread runner;
}




