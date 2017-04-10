/**
   @version 1.11 2001-08-27
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
   This program demonstrates formatting dates under
   various locales.
*/
public class DateFormatTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new DateFormatFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains combo boxes to pick a locale, date and
   time formats, text fields to display formatted date and time,
   buttons to parse the text field contents, and a "lenient"
   check box.
*/
class DateFormatFrame extends JFrame
{  
   public DateFormatFrame()
   {  
      setSize(WIDTH, HEIGHT);
      setTitle("DateFormatTest");

      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.EAST;
      add(new JLabel("Locale"), gbc, 0, 0, 1, 1);
      add(new JLabel("Date style"), gbc, 0, 1, 1, 1);
      add(new JLabel("Time style"), gbc, 2, 1, 1, 1);
      add(new JLabel("Date"), gbc, 0, 2, 1, 1);
      add(new JLabel("Time"), gbc, 0, 3, 1, 1);
      gbc.anchor = GridBagConstraints.WEST;
      add(localeCombo, gbc, 1, 0, 2, 1);
      add(dateStyleCombo, gbc, 1, 1, 1, 1);
      add(timeStyleCombo, gbc, 3, 1, 1, 1);
      add(dateParseButton, gbc, 3, 2, 1, 1);
      add(timeParseButton, gbc, 3, 3, 1, 1);
      add(lenientCheckbox, gbc, 0, 4, 2, 1);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      add(dateText, gbc, 1, 2, 2, 1);
      add(timeText, gbc, 1, 3, 2, 1);

      locales = DateFormat.getAvailableLocales();
      for (int i = 0; i < locales.length; i++)
         localeCombo.addItem(locales[i].getDisplayName());
      localeCombo.setSelectedItem(
         Locale.getDefault().getDisplayName());
      currentDate = new Date();
      currentTime = new Date();
      updateDisplay();

      ActionListener listener = new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               updateDisplay();
            }
         };

      localeCombo.addActionListener(listener);
      dateStyleCombo.addActionListener(listener);
      timeStyleCombo.addActionListener(listener);

      dateParseButton.addActionListener(new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               String d = dateText.getText();
               try
               {  
                  currentDateFormat.setLenient
                     (lenientCheckbox.isSelected());
                  Date date = currentDateFormat.parse(d);
                  currentDate = date;
                  updateDisplay();
               }
               catch(ParseException e)
               {  
                  dateText.setText("Parse error: " + d);
               }
               catch(IllegalArgumentException e)
               {  
                  dateText.setText("Argument error: " + d);
               }
            }
         });

      timeParseButton.addActionListener(new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               String t = timeText.getText();
               try
               {  
                  currentDateFormat.setLenient
                     (lenientCheckbox.isSelected());
                  Date date = currentTimeFormat.parse(t);
                  currentTime = date;
                  updateDisplay();
               }
               catch(ParseException e)
               {  
                  timeText.setText("Parse error: " + t);
               }
               catch(IllegalArgumentException e)
               {  
                  timeText.setText("Argument error: " + t);
               }
            }
         });
   }

   /**
      A convenience method to add a component to given grid bag
      layout locations.
      @param c the component to add
      @param gbc the grid bag constraints to use
      @param x the x grid position
      @param y the y grid position
      @param w the grid width
      @param h the grid height
   */
   public void add(Component c, GridBagConstraints gbc,
      int x, int y, int w, int h)
   {  
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      getContentPane().add(c, gbc);
   }

   /**
      Updates the display and formats the date according
      to the user settings.
   */
   public void updateDisplay()
   {  
      Locale currentLocale = locales[
         localeCombo.getSelectedIndex()];
      int dateStyle = dateStyleCombo.getValue();
      currentDateFormat
         = DateFormat.getDateInstance(dateStyle,
         currentLocale);
      String d = currentDateFormat.format(currentDate);
      dateText.setText(d);
      int timeStyle = timeStyleCombo.getValue();
      currentTimeFormat
         = DateFormat.getTimeInstance(timeStyle,
         currentLocale);
      String t = currentTimeFormat.format(currentTime);
      timeText.setText(t);
   }

   private Locale[] locales;

   private Date currentDate;
   private Date currentTime;
   private DateFormat currentDateFormat;
   private DateFormat currentTimeFormat;

   private JComboBox localeCombo = new JComboBox();
   private EnumCombo dateStyleCombo
      = new EnumCombo(DateFormat.class,
        new String[] { "Default", "Full", "Long",
        "Medium", "Short" });
   private EnumCombo timeStyleCombo
      = new EnumCombo(DateFormat.class,
        new String[] { "Default", "Full", "Long",
        "Medium", "Short" });
   private JButton dateParseButton = new JButton("Parse date");
   private JButton timeParseButton = new JButton("Parse time");
   private JTextField dateText = new JTextField(30);
   private JTextField timeText = new JTextField(30);
   private JTextField parseText = new JTextField(30);
   private JCheckBox lenientCheckbox
      = new JCheckBox("Parse lenient", true);
   private static final int WIDTH = 400;
   private static final int HEIGHT = 200;
}

/**
   A combo box that lets users choose from among static field
   values whose names are given in the constructor.
*/
class EnumCombo extends JComboBox
{ 
   /**
      Constructs an EnumCombo.
      @param cl a class
      @param labels an array of static field names of cl
   */
   public EnumCombo(Class cl, String[] labels)
   {  
      for (int i = 0; i < labels.length; i++)
      {  
         String label = labels[i];
         String name = label.toUpperCase().replace(' ', '_');
         int value = 0;
         try
         {  
            java.lang.reflect.Field f = cl.getField(name);
            value = f.getInt(cl);
         }
         catch(Exception e)
         {  
            label = "(" + label + ")";
         }
         table.put(label, new Integer(value));
         addItem(label);
      }
      setSelectedItem(labels[0]);
   }

   /**
      Returns the value of the field that the user selected.
      @return the static field value
   */
   public int getValue()
   {  
      return ((Integer)table.get(getSelectedItem())).intValue();
   }

   private Map table = new HashMap();
}
