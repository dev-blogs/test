/**
   @version 1.01 2001-05-25
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.Timer;

/**
   This program demonstrates the use of a progress bar
   to monitor the progress of a thread.
*/
public class ProgressBarTest
{  
   public static void main(String[] args)
   {  
      ProgressBarFrame frame = new ProgressBarFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame that contains a button to launch a simulated activity,
   a progress bar, and a text area for the activity output.
*/
class ProgressBarFrame extends JFrame
{  
   public ProgressBarFrame()
   {  
      setTitle("ProgressBarTest");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();

      // this text area holds the activity output
      textArea = new JTextArea();

      // set up panel with button and progress bar

      JPanel panel = new JPanel();
      startButton = new JButton("Start");
      progressBar = new JProgressBar();
      progressBar.setStringPainted(true);
      panel.add(startButton);
      panel.add(progressBar);
      contentPane.add(new JScrollPane(textArea), 
         BorderLayout.CENTER);
      contentPane.add(panel, BorderLayout.SOUTH); 

      // set up the button action

      startButton.addActionListener(new 
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               progressBar.setMaximum(1000);
               activity = new SimulatedActivity(1000);
               activity.start();
               activityMonitor.start();
               startButton.setEnabled(false);
            }
         });


      // set up the timer action

      activityMonitor = new Timer(500, new 
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               int current = activity.getCurrent();
               
               // show progress
               textArea.append(current + "\n");
               progressBar.setValue(current);
               
               // check if task is completed
               if (current == activity.getTarget())
               {  
                  activityMonitor.stop();
                  startButton.setEnabled(true);
               }
            }
         });
   }

   private Timer activityMonitor;
   private JButton startButton;
   private JProgressBar progressBar;
   private JTextArea textArea;
   private SimulatedActivity activity;

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;
}

/**
   A similated activity thread.
*/
class SimulatedActivity extends Thread
{ 
   /**
      Constructs the simulated activity thread object. The
      thread increments a counter from 0 to a given target.
      @param t the target value of the counter.
   */
   public SimulatedActivity(int t)
   {  
      current = 0;
      target = t;
   }

   public int getTarget()
   {  
      return target;
   }

   public int getCurrent()
   {  
      return current;
   }

   public void run()
   {  
      try
      {
         while (current < target && !interrupted())
         {    
            sleep(100);
            current++;
         }
      }
      catch(InterruptedException e)
      {  
      }
   }

   private int current;
   private int target;
}







