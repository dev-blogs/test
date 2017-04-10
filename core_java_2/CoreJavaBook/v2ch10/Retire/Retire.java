/**
   @version 1.2 1999-10-13
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.applet.*;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.*;

/**
   This applet shows a retirement calculator. The UI is displayed
   in English, German, and Chinese.
*/
public class Retire extends JApplet
{
   public void init()
   {
      GridBagLayout gbl = new GridBagLayout();
      getContentPane().setLayout(gbl);

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 100;
      gbc.weighty = 0;

      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.EAST;
      add(languageLabel, gbc, 0, 0, 1, 1);
      add(savingsLabel, gbc, 0, 1, 1, 1);
      add(contribLabel, gbc, 2, 1, 1, 1);
      add(incomeLabel, gbc, 4, 1, 1, 1);
      add(currentAgeLabel, gbc, 0, 2, 1, 1);
      add(retireAgeLabel, gbc, 2, 2, 1, 1);
      add(deathAgeLabel, gbc, 4, 2, 1, 1);
      add(inflationPercentLabel, gbc, 0, 3, 1, 1);
      add(investPercentLabel, gbc, 2, 3, 1, 1);

      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.WEST;
      add(localeCombo, gbc, 1, 0, 2, 1);
      add(savingsField, gbc, 1, 1, 1, 1);
      add(contribField, gbc, 3, 1, 1, 1);
      add(incomeField, gbc, 5, 1, 1, 1);
      add(currentAgeField, gbc, 1, 2, 1, 1);
      add(retireAgeField, gbc, 3, 2, 1, 1);
      add(deathAgeField, gbc, 5, 2, 1, 1);
      add(inflationPercentField, gbc, 1, 3, 1, 1);
      add(investPercentField, gbc, 3, 3, 1, 1);

      computeButton.setName("computeButton");
      computeButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               getInfo();
               updateData();
               updateGraph();
            }
         });
      add(computeButton, gbc, 5, 3, 1, 1);

      gbc.weighty = 100;
      gbc.fill = GridBagConstraints.BOTH;
      add(retireCanvas, gbc, 0, 4, 4, 1);
      add(new JScrollPane(retireText), gbc, 4, 4, 2, 1);
      retireText.setEditable(false);
      retireText.setFont(new Font("Monospaced", Font.PLAIN, 10));

      info.setSavings(0);
      info.setContrib(9000);
      info.setIncome(60000);
      info.setCurrentAge(35);
      info.setRetireAge(65);
      info.setDeathAge(85);
      info.setInvestPercent(0.1);
      info.setInflationPercent(0.05);

      localeCombo.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               setCurrentLocale(
                  locales[localeCombo.getSelectedIndex()]);
            }
         });

      locales = new Locale[]
         { Locale.US, Locale.CHINA, Locale.GERMANY };

      int localeIndex = 0; // US locale is default selection

      for (int i = 0; i < locales.length; i++)
         // if current locale one of the choices, we'll select it
         if (getLocale().equals(locales[i])) localeIndex = i;

      setCurrentLocale(locales[localeIndex]);
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
      Sets the current locale.
      @param locale the desired locale
   */
   public void setCurrentLocale(Locale locale)
   {
      currentLocale = locale;

      int localeIndex = localeCombo.getSelectedIndex();
      localeCombo.removeAllItems();
      for (int i = 0; i < locales.length; i++)
      {
         String language
            = locales[i].getDisplayLanguage(currentLocale);
         localeCombo.addItem(language);
      }
      if (localeIndex >= 0)
         localeCombo.setSelectedIndex(localeIndex);

      res = ResourceBundle.getBundle("RetireResources",
         currentLocale);
      resStrings = ResourceBundle.getBundle("RetireStrings",
         currentLocale);
      currencyFmt
         = NumberFormat.getCurrencyInstance(currentLocale);
      numberFmt
         = NumberFormat.getNumberInstance(currentLocale);
      percentFmt
         = NumberFormat.getPercentInstance(currentLocale);

      updateDisplay();
      updateInfo();
      updateData();
      updateGraph();
   }

   /**
      Updates all labels in the display.
   */
   public void updateDisplay()
   {
      languageLabel.setText(resStrings.getString("language"));
      savingsLabel.setText(resStrings.getString("savings"));
      contribLabel.setText(resStrings.getString("contrib"));
      incomeLabel.setText(resStrings.getString("income"));
      currentAgeLabel.setText(
         resStrings.getString("currentAge"));
      retireAgeLabel.setText(resStrings.getString("retireAge"));
      deathAgeLabel.setText(resStrings.getString("deathAge"));
      inflationPercentLabel.setText(
         resStrings.getString("inflationPercent"));
      investPercentLabel.setText(
         resStrings.getString("investPercent"));
      computeButton.setText(
         resStrings.getString("computeButton"));

      validate();
   }

   /**
      Updates the information in the text fields.
   */
   public void updateInfo()
   {
      savingsField.setText(
         currencyFmt.format(info.getSavings()));
      contribField.setText(
         currencyFmt.format(info.getContrib()));
      incomeField.setText(currencyFmt.format(info.getIncome()));
      currentAgeField.setText(
         numberFmt.format(info.getCurrentAge()));
      retireAgeField.setText(
         numberFmt.format(info.getRetireAge()));
      deathAgeField.setText(
         numberFmt.format(info.getDeathAge()));
      investPercentField.setText(
         percentFmt.format(info.getInvestPercent()));
      inflationPercentField.setText(
         percentFmt.format(info.getInflationPercent()));
   }

   /**
      Updates the data displayed in the text area.
   */
   public void updateData()
   {
      retireText.setText("");
      MessageFormat retireMsg = new MessageFormat("");
      retireMsg.setLocale(currentLocale);
      retireMsg.applyPattern(resStrings.getString("retire"));

      for (int i = info.getCurrentAge();
           i <= info.getDeathAge(); i++)
      {
         Object[] args = { new Integer(i),
            new Double(info.getBalance(i)) };
         retireText.append(retireMsg.format(args) + "\n");
      }
   }

   /**
      Updates the graph.
   */
   public void updateGraph()
   {
      retireCanvas.setColorPre(
         (Color)res.getObject("colorPre"));
      retireCanvas.setColorGain(
         (Color)res.getObject("colorGain"));
      retireCanvas.setColorLoss(
         (Color)res.getObject("colorLoss"));
      retireCanvas.setInfo(info);
      repaint();
   }

   /**
      Reads the user input from the text fields.
   */
   public void getInfo()
   {
      try
      {
         info.setSavings(currencyFmt.parse(
            savingsField.getText()).doubleValue());
         info.setContrib(currencyFmt.parse(
            contribField.getText()).doubleValue());
         info.setIncome(currencyFmt.parse(
            incomeField.getText()).doubleValue());
         info.setCurrentAge(numberFmt.parse(
            currentAgeField.getText()).intValue());
         info.setRetireAge(numberFmt.parse(
            retireAgeField.getText()).intValue());
         info.setDeathAge(numberFmt.parse(
            deathAgeField.getText()).intValue());
         info.setInvestPercent(percentFmt.parse(
            investPercentField.getText()).doubleValue());
         info.setInflationPercent(percentFmt.parse(
            inflationPercentField.getText()).doubleValue());
      }
      catch (ParseException exception)
      {
      }
   }

   private JTextField savingsField = new JTextField(10);
   private JTextField contribField = new JTextField(10);
   private JTextField incomeField = new JTextField(10);
   private JTextField currentAgeField = new JTextField(4);
   private JTextField retireAgeField = new JTextField(4);
   private JTextField deathAgeField = new JTextField(4);
   private JTextField inflationPercentField = new JTextField(6);
   private JTextField investPercentField = new JTextField(6);
   private JTextArea retireText = new JTextArea(10, 25);
   private RetireCanvas retireCanvas = new RetireCanvas();
   private JButton computeButton = new JButton();
   private JLabel languageLabel = new JLabel();
   private JLabel savingsLabel = new JLabel();
   private JLabel contribLabel = new JLabel();
   private JLabel incomeLabel = new JLabel();
   private JLabel currentAgeLabel = new JLabel();
   private JLabel retireAgeLabel = new JLabel();
   private JLabel deathAgeLabel = new JLabel();
   private JLabel inflationPercentLabel = new JLabel();
   private JLabel investPercentLabel = new JLabel();

   private RetireInfo info = new RetireInfo();

   private Locale[] locales;
   private Locale currentLocale;
   private JComboBox localeCombo = new JComboBox();
   private ResourceBundle res;
   private ResourceBundle resStrings;
   private NumberFormat currencyFmt;
   private NumberFormat numberFmt;
   private NumberFormat percentFmt;
}

/**
   The information required to compute retirement income data.
*/
class RetireInfo
{
   /**
      Gets the available balance for a given year.
      @param year the year for which to compute the balance
      @return the amount of money available (or required) in
      that year
   */
   public double getBalance(int year)
   {
      if (year < currentAge) return 0;
      else if (year == currentAge)
      {
         age = year;
         balance = savings;
         return balance;
      }
      else if (year == age)
         return balance;
      if (year != age + 1)
         getBalance(year - 1);
      age = year;
      if (age < retireAge)
         balance += contrib;
      else
         balance -= income;
      balance = balance
         * (1 + (investPercent - inflationPercent));
      return balance;
   }

   /**
      Gets the amount of prior savings.
      @return the savings amount
   */
   public double getSavings()
   {
      return savings;
   }

   /**
      Sets the amount of prior savings.
      @param x the savings amount
   */
   public void setSavings(double x)
   {
      savings = x;
   }

   /**
      Gets the annual contribution to the retirement account.
      @return the contribution amount
   */
   public double getContrib()
   {
      return contrib;
   }

   /**
      Sets the annual contribution to the retirement account.
      @param x the contribution amount
   */
   public void setContrib(double x)
   {
      contrib = x;
   }

   /**
      Gets the annual income.
      @return the income amount
   */
   public double getIncome()
   {
      return income;
   }

   /**
      Sets the annual income.
      @param x the income amount
   */
   public void setIncome(double x)
   {
      income = x;
   }

   /**
      Gets the current age.
      @return the age
   */
   public int getCurrentAge()
   {
      return currentAge;
   }

   /**
      Sets the current age.
      @param x the age
   */
   public void setCurrentAge(int x)
   {
      currentAge = x;
   }

   /**
      Gets the desired retirement age.
      @return the age
   */
   public int getRetireAge()
   {
      return retireAge;
   }

   /**
      Sets the desired retirement age.
      @param x the age
   */
   public void setRetireAge(int x)
   {
      retireAge = x;
   }

   /**
      Gets the expected age of death.
      @return the age
   */
   public int getDeathAge()
   {
      return deathAge;
   }

   /**
      Sets the expected age of death.
      @param x the age
   */
   public void setDeathAge(int x)
   {
      deathAge = x;
   }

   /**
      Gets the estimated percentage of inflation.
      @return the percentage
   */
   public double getInflationPercent()
   {
      return inflationPercent;
   }

   /**
      Sets the estimated percentage of inflation.
      @param x the percentage
   */
   public void setInflationPercent(double x)
   {
      inflationPercent = x;
   }

   /**
      Gets the estimated yield of the investment.
      @return the percentage
   */
   public double getInvestPercent()
   {
      return investPercent;
   }

   /**
      Sets the estimated yield of the investment.
      @param x the percentage
   */
   public void setInvestPercent(double x)
   {
      investPercent = x;
   }

   private double savings;
   private double contrib;
   private double income;
   private int currentAge;
   private int retireAge;
   private int deathAge;
   private double inflationPercent;
   private double investPercent;

   private int age;
   private double balance;
}

/**
   This panel draws a graph of the investment result.
*/
class RetireCanvas extends JPanel
{
   public RetireCanvas()
   {
      setSize(WIDTH, HEIGHT);
   }

   /**
      Sets the retirement information to be plotted.
      @param newInfo the new retirement info.
   */
   public void setInfo(RetireInfo newInfo)
   {
      info = newInfo;
      repaint();
   }

   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D)g;
      if (info == null) return;

      double minValue = 0;
      double maxValue = 0;
      int i;
      for (i = info.getCurrentAge();
           i <= info.getDeathAge(); i++)
      {
         double v = info.getBalance(i);
         if (minValue > v) minValue = v;
         if (maxValue < v) maxValue = v;
      }
      if (maxValue == minValue) return;

      int barWidth = getWidth() / (info.getDeathAge()
         - info.getCurrentAge() + 1);
      double scale = getHeight() / (maxValue - minValue);

      for (i = info.getCurrentAge();
           i <= info.getDeathAge(); i++)
      {
         int x1 = (i - info.getCurrentAge()) * barWidth + 1;
         int y1;
         double v = info.getBalance(i);
         int height;
         int yOrigin = (int)(maxValue * scale);

         if (v >= 0)
         {
            y1 = (int)((maxValue - v) * scale);
            height = yOrigin - y1;
         }
         else
         {
            y1 = yOrigin;
            height = (int)(-v * scale);
         }

         if (i < info.getRetireAge())
            g2.setColor(colorPre);
         else if (v >= 0)
            g2.setColor(colorGain);
         else
            g2.setColor(colorLoss);
         Rectangle2D bar = new Rectangle2D.Double(x1, y1,
            barWidth - 2, height);
         g2.fill(bar);
         g2.setColor(Color.black);
         g2.draw(bar);
      }
   }

   /**
      Sets the color to be used before retirement.
      @param color the desired color
   */
   public void setColorPre(Color color)
   {
      colorPre = color;
      repaint();
   }

   /**
      Sets the color to be used after retirement while
      the account balance is positive.
      @param color the desired color
   */
   public void setColorGain(Color color)
   {
      colorGain = color;
      repaint();
   }

   /**
      Sets the color to be used after retirement when
      the account balance is negative.
      @param color the desired color
   */
   public void setColorLoss(Color color)
   {
      colorLoss = color;
      repaint();
   }

   private RetireInfo info = null;

   private Color colorPre;
   private Color colorGain;
   private Color colorLoss;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 200;
}
