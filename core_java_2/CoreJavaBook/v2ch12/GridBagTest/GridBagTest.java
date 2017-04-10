/**
   @version 1.00 2001-09-06
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   This program shows how to use an XML file to describe
   a gridbag layout
*/
public class GridBagTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new FontFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a font selection dialog that
   is described by an XML file.
*/
class FontFrame extends JFrame
{  
   public FontFrame()
   {  
      setSize(WIDTH, HEIGHT);
      setTitle("GridBagTest");

      gridbag = new GridBagPane("fontdialog.xml");
      setContentPane(gridbag);

      face = (JComboBox)gridbag.get("face");
      size = (JComboBox)gridbag.get("size");
      bold = (JCheckBox)gridbag.get("bold");
      italic = (JCheckBox)gridbag.get("italic");

      face.setModel(new DefaultComboBoxModel(new Object[]
         {
            "Serif", "SansSerif", "Monospaced",
            "Dialog", "DialogInput"
         }));
         
      size.setModel(new DefaultComboBoxModel(new Object[]
         {
            "8", "10", "12", "15", "18", "24", "36", "48" 
         }));

      ActionListener listener = new
         ActionListener()
         {  
            public void actionPerformed(ActionEvent event)
            {  
               setSample();
            }
         };

      face.addActionListener(listener);
      size.addActionListener(listener);
      bold.addActionListener(listener);
      italic.addActionListener(listener);

      setSample();
   }

   /**
      This method sets the text sample to the selected font.
   */
   public void setSample()
   {
      String fontFace = (String)face.getSelectedItem();
      int fontSize = Integer.parseInt(
         (String)size.getSelectedItem());
      JTextArea sample = (JTextArea)gridbag.get("sample");
      int fontStyle 
         = (bold.isSelected() ? Font.BOLD : 0) 
         + (italic.isSelected() ? Font.ITALIC : 0);
      
      sample.setFont(new Font(fontFace, fontStyle, fontSize));
      sample.repaint();      
   }

   private GridBagPane gridbag;
   private JComboBox face;
   private JComboBox size;
   private JCheckBox bold;
   private JCheckBox italic;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 400;
}
