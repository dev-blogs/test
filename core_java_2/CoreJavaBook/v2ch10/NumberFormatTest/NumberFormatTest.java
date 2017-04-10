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
   This program demonstrates formatting numbers under
   various locales.
*/
public class NumberFormatTest
{
   public static void main(String[] args)
   {
      JFrame frame = new NumberFormatFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains radio buttons to select a number format,
   a combo box to pick a locale, a text field to display
   a formatted number, and a button to parse the text field
   contents.
*/
class NumberFormatFrame extends JFrame
{
   public NumberFormatFrame()
   {
      setSize(WIDTH, HEIGHT);
      setTitle("NumberFormatTest");

      getContentPane().setLayout(new GridBagLayout());

      ActionListener listener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               updateDisplay();
            }
         };

      JPanel p = new JPanel();
      addRadioButton(p, numberRadioButton, rbGroup, listener);
      addRadioButton(p, currencyRadioButton, rbGroup, listener);
      addRadioButton(p, percentRadioButton, rbGroup, listener);

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.EAST;
      add(new JLabel("Locale"), gbc, 0, 0, 1, 1);
      add(p, gbc, 1, 1, 1, 1);
      add(parseButton, gbc, 0, 2, 1, 1);
      gbc.anchor = GridBagConstraints.WEST;
      add(localeCombo, gbc, 1, 0, 1, 1);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      add(numberText, gbc, 1, 2, 1, 1);

      locales = NumberFormat.getAvailableLocales();
      for (int i = 0; i < locales.length; i++)
         localeCombo.addItem(locales[i].getDisplayName());
      localeCombo.setSelectedItem(
         Locale.getDefault().getDisplayName());
      currentNumber = 123456.78;
      updateDisplay();

      localeCombo.addActionListener(listener);

      parseButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               String s = numberText.getText();
               try
               {
                  Number n = currentNumberFormat.parse(s);
                  if (n != null)
                  {
                     currentNumber = n.doubleValue();
                     updateDisplay();
                  }
                  else
                  {
                     numberText.setText("Parse error: " + s);
                  }
               }
               catch(ParseException e)
               {
                  numberText.setText("Parse error: " + s);
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
      Adds a radio button to a container.
      @param p the container into which to place the button
      @param b the button
      @param g the button group
      @param listener the button listener
   */
   public void addRadioButton(Container p, JRadioButton b,
      ButtonGroup g, ActionListener listener)
   {
      b.setSelected(g.getButtonCount() == 0);
      b.addActionListener(listener);
      g.add(b);
      p.add(b);
   }

   /**
      Updates the display and formats the number according
      to the user settings.
   */
   public void updateDisplay()
   {
      Locale currentLocale = locales[
         localeCombo.getSelectedIndex()];
      currentNumberFormat = null;
      if (numberRadioButton.isSelected())
         currentNumberFormat
            = NumberFormat.getNumberInstance(currentLocale);
      else if (currencyRadioButton.isSelected())
         currentNumberFormat
            = NumberFormat.getCurrencyInstance(currentLocale);
      else if (percentRadioButton.isSelected())
         currentNumberFormat
            = NumberFormat.getPercentInstance(currentLocale);
      String n = currentNumberFormat.format(currentNumber);
      numberText.setText(n);
   }

   private Locale[] locales;

   private double currentNumber;

   private JComboBox localeCombo = new JComboBox();
   private JButton parseButton = new JButton("Parse");
   private JTextField numberText = new JTextField(30);
   private JRadioButton numberRadioButton
      = new JRadioButton("Number");
   private JRadioButton currencyRadioButton
      = new JRadioButton("Currency");
   private JRadioButton percentRadioButton
      = new JRadioButton("Percent");
   private ButtonGroup rbGroup = new ButtonGroup();
   private NumberFormat currentNumberFormat;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 200;
}
