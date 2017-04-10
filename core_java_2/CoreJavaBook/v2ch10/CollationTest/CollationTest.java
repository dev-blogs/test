/**
   @version 1.11 2001-08-27
   @author Cay Horstmann
*/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
   This program demonstrates collating strings under
   various locales.
*/
public class CollationTest
{
   public static void main(String[] args)
   {
      JFrame frame = new CollationFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains combo boxes to pick a locale, collation
   strength and decomposition rules, a text field and button
   to add new strings, and a text area to list the collated
   strings.
*/
class CollationFrame extends JFrame
{
   public CollationFrame()
   {
      setSize(WIDTH, HEIGHT);
      setTitle("CollationTest");

      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.EAST;
      add(new JLabel("Locale"), gbc, 0, 0, 1, 1);
      add(new JLabel("Strength"), gbc, 0, 1, 1, 1);
      add(new JLabel("Decomposition"), gbc, 0, 2, 1, 1);
      add(addButton, gbc, 0, 3, 1, 1);
      gbc.anchor = GridBagConstraints.WEST;
      add(localeCombo, gbc, 1, 0, 1, 1);
      add(strengthCombo, gbc, 1, 1, 1, 1);
      add(decompositionCombo, gbc, 1, 2, 1, 1);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      add(newWord, gbc, 1, 3, 1, 1);
      gbc.fill = GridBagConstraints.BOTH;
      add(new JScrollPane(sortedWords), gbc, 1, 4, 1, 1);

      locales = Collator.getAvailableLocales();
      for (int i = 0; i < locales.length; i++)
         localeCombo.addItem(locales[i].getDisplayName());
      localeCombo.setSelectedItem(
         Locale.getDefault().getDisplayName());

      strings.add("America");
      strings.add("ant");
      strings.add("Zulu");
      strings.add("zebra");
      strings.add("?strom");
      strings.add("Angstrom");
      strings.add("Ant");
      updateDisplay();

      addButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               strings.add(newWord.getText());
               updateDisplay();
            }
         });

      ActionListener listener = new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               updateDisplay();
            }
         };

      localeCombo.addActionListener(listener);
      strengthCombo.addActionListener(listener);
      decompositionCombo.addActionListener(listener);
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
      Updates the display and collates the strings according
      to the user settings.
   */
   public void updateDisplay()
   {
      Locale currentLocale = locales[
         localeCombo.getSelectedIndex()];

      currentCollator
         = Collator.getInstance(currentLocale);
      currentCollator.setStrength(strengthCombo.getValue());
      currentCollator.setDecomposition(
         decompositionCombo.getValue());

      Collections.sort(strings, currentCollator);

      sortedWords.setText("");
      for (int i = 0; i < strings.size(); i++)
      {
         String s = (String)strings.get(i);
         if (i > 0
            && currentCollator.compare(s, strings.get(i - 1)) == 0)
         {
            sortedWords.append("= ");
         }
         sortedWords.append(s + "\n");
      }
   }

   private Locale[] locales;
   private List strings = new ArrayList();
   private Collator currentCollator;

   private JComboBox localeCombo = new JComboBox();
   private EnumCombo strengthCombo
      = new EnumCombo(Collator.class,
        new String[] { "Primary", "Secondary", "Tertiary" });
   private EnumCombo decompositionCombo
      = new EnumCombo(Collator.class,
        new String[] { "Canonical Decomposition",
        "Full Decomposition", "No Decomposition" });
   private JTextField newWord = new JTextField(20);
   private JTextArea sortedWords = new JTextArea(10, 20);
   private JButton addButton = new JButton("Add");
   private static final int WIDTH = 400;
   private static final int HEIGHT = 400;
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
