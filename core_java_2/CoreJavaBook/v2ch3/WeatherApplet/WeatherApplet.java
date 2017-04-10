/**
   @version 1.11 2001-06-27
   @author Cay Horstmann
*/

import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

/**
   This applet displays weather data from a NOAA server.
   The data is mostly in text format, so it can be displayed
   in a text area.
*/
public class WeatherApplet extends JApplet
{ 
   public void init()
   {  
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      // Set up the lists of choices for states and reports
      JPanel comboPanel = new JPanel();
      state = makeCombo(states, comboPanel);
      report = makeCombo(reports, comboPanel);
      contentPane.add(comboPanel, BorderLayout.NORTH);

      // Add the text area
      weather = new JTextArea(20, 80);
      weather.setFont(new Font("Courier", Font.PLAIN, 12));

      // Add the report button
      contentPane.add(new JScrollPane(weather), 
         BorderLayout.CENTER);
      JPanel buttonPanel = new JPanel();
      JButton reportButton = new JButton("Get report");
      reportButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {  
               weather.setText("");
               new 
                  Thread()
                  {
                     public void run()
                     {
                        getWeather(getItem(state, states), 
                           getItem(report, reports));
                     }
                  }.start();
            }
         });

      buttonPanel.add(reportButton);
      contentPane.add(buttonPanel, BorderLayout.SOUTH);
   }

   /**
      Makes a combo box.
      @param items the array of strings whose first column
      contains the combo box display entries
      @param parent the parent to add the combo box to
   */
   public JComboBox makeCombo(String[][] items, Container parent)
   {
      JComboBox combo = new JComboBox();
      for (int i = 0; i < items.length; i++)
         combo.addItem(items[i][0]);
      parent.add(combo);
      return combo;
   }
   
   /**
      Gets the query string for a combo box display entry.
      @param box the combo box
      @param items the array of strings whose first column
      contains the combo box display entries and whose second
      column contains the corresponding query strings
      @return the query string
   */
   public String getItem(JComboBox box, String[][] items)
   {  
      return items[box.getSelectedIndex()][1];
   }

   /**
      Puts together the URL query and fills the text area with
      the data.
      @param state the state part of the query
      @param report the report part of the query
   */
   public void getWeather(String state, String report)
   {  
      String r = new String();
      try
      {  
         String queryBase = getParameter("queryBase");
         String query
            = queryBase + state + "/" + report + ".html";
         URL url = new URL(query);
         BufferedReader in = new BufferedReader(new
            InputStreamReader(url.openStream()));

         String line;
         while ((line = in.readLine()) != null)
            weather.append(removeTags(line) + "\n");
      }
      catch(IOException e)
      {  
         showStatus("Error " + e);
      }
   }

   /**
      Removes HTML tags from a string.
      @param s a string
      @return s with <...> tags removed.
   */
   public static String removeTags(String s)
   {  
      while (true)
      {  
         int lb = s.indexOf('<');
         if (lb < 0) return s;
         int rb = s.indexOf('>', lb);
         if (rb < 0) return s;
         s = s.substring(0, lb) + " " + s.substring(rb + 1);
      }
   }

   private JTextArea weather;
   private JComboBox state;
   private JComboBox report;

   private String[][] states =
      {  
         { "Alabama", "al" },
         { "Alaska", "ak" },
         { "Arizona", "az" },
         { "Arkansas", "ar" },
         { "California", "ca" },
         { "Colorado", "co" },
         { "Connecticut", "ct" },
         { "Delaware", "de" },
         { "Florida", "fl" },
         { "Georgia", "ga" },
         { "Hawaii", "hi" },
         { "Idaho", "id" },
         { "Illinois", "il" },
         { "Indiana", "in" },
         { "Iowa", "ia" },
         { "Kansas", "ks" },
         { "Kentucky", "ky" },
         { "Lousisiana", "la" },
         { "Maine", "me" },
         { "Maryland", "md" },
         { "Massachusetts", "ma" },
         { "Michigan", "mi" },
         { "Minnesota", "mn" },
         { "Mississippi", "ms" },
         { "Missouri", "mo" },
         { "Montana", "mt" },
         { "Nebraska", "ne" },
         { "Nevada", "nv" },
         { "New Hampshire", "nh" },
         { "New Jersey", "nj" },
         { "New Mexico", "nm" },
         { "New York", "ny" },
         { "North Carolina", "nc" },
         { "North Dakota", "nd" },
         { "Ohio", "oh" },
         { "Oklahoma", "ok" },
         { "Oregon", "or" },
         { "Pennsylvania", "pa" },
         { "Rhode Island", "ri" },
         { "South Carolina", "sc" },
         { "South Dakota", "sd" },
         { "Tennessee", "tn" },
         { "Texas", "tx" },
         { "Utah", "ut" },
         { "Vermont", "vt" },
         { "Virginia", "va" },
         { "Washington", "wa" },
         { "West Virginia", "wv" },
         { "Wisconsin", "wi" },
         { "Wyoming", "wy" }
      };

   private String[][] reports =
      {  
         { "Hourly (State Weather Roundup)", "hourly" },
         { "State Forecast", "state" },
         { "Zone Forecast", "zone" },
         { "Short Term (NOWCASTS)", "shortterm" },
         { "Forecast Discussion", "discussion" },
         { "Weather Summary", "summary" },
         { "Public Information", "public" },
         { "Climate Data", "climate" },
         { "Hydrological Data", "hydro" },
         { "Watches", "watches" },
         { "Special Weather Statements", "special" },
         { "Warnings and Advisories", "allwarnings" }
      };
}



