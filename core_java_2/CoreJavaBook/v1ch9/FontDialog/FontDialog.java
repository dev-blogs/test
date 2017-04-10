/**
   @version 1.30 2000-05-12
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class FontDialog
{
   public static void main(String[] args)
   {  
      FontDialogFrame frame = new FontDialogFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame that uses a grid bag layout to arrange font
   selection components.
*/
class FontDialogFrame extends JFrame
{  
   public FontDialogFrame()
   {  
      setTitle("FontDialog");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();
      GridBagLayout layout = new GridBagLayout();
      contentPane.setLayout(layout);

      ActionListener listener = new FontAction();

      // construct components
      
      JLabel faceLabel = new JLabel("Face: ");

      face = new JComboBox(new String[] 
         {  
            "Serif", "SansSerif", "Monospaced", 
            "Dialog", "DialogInput" 
         });
      
      face.addActionListener(listener);

      JLabel sizeLabel = new JLabel("Size: ");

      size = new JComboBox(new String[]
         {
            "8", "10", "12", "15", "18", "24", "36", "48"
         });

      size.addActionListener(listener);

      bold = new JCheckBox("Bold");
      bold.addActionListener(listener);

      italic = new JCheckBox("Italic");
      italic.addActionListener(listener);

      sample = new JTextArea();
      sample.setText(
         "The quick brown fox jumps over the lazy dog");
      sample.setEditable(false);
      sample.setLineWrap(true);
      sample.setBorder(BorderFactory.createEtchedBorder());

      // add components to grid

      GridBagConstraints constraints = new GridBagConstraints();

      constraints.fill = GridBagConstraints.NONE;
      constraints.anchor = GridBagConstraints.EAST;
      constraints.weightx = 0;
      constraints.weighty = 0;

      add(faceLabel, constraints, 0, 0, 1, 1);
      add(sizeLabel, constraints, 0, 1, 1, 1);

      constraints.fill = GridBagConstraints.HORIZONTAL;
      constraints.weightx = 100;
      
      add(face, constraints, 1, 0, 1, 1);
      add(size, constraints, 1, 1, 1, 1);

      constraints.weighty = 100;
      constraints.fill = GridBagConstraints.NONE;
      constraints.anchor = GridBagConstraints.CENTER;

      add(bold, constraints, 0, 2, 2, 1);
      add(italic, constraints, 0, 3, 2, 1);

      constraints.fill = GridBagConstraints.BOTH;
      add(sample, constraints, 2, 0, 1, 4);
   }
  
   /**
      A convenience method to add a component to given grid bag
      layout locations.
      @param c the component to add
      @param constraints the grid bag constraints to use
      @param x the x grid position
      @param y the y grid position
      @param w the grid width
      @param h the grid height
   */
   public void add(Component c, GridBagConstraints constraints,
      int x, int y, int w, int h)
   {  
      constraints.gridx = x;
      constraints.gridy = y;
      constraints.gridwidth = w;
      constraints.gridheight = h;
      getContentPane().add(c, constraints);
   }

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  

   private JComboBox face;
   private JComboBox size;
   private JCheckBox bold;
   private JCheckBox italic;
   private JTextArea sample;

   /**
      An action listener that changes the font of the 
      sample text.
   */
   private class FontAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {  
         String fontFace = (String)face.getSelectedItem();
         int fontStyle = (bold.isSelected() ? Font.BOLD : 0)
            + (italic.isSelected() ? Font.ITALIC : 0);
         int fontSize = Integer.parseInt(
            (String)size.getSelectedItem());
         Font font = new Font(fontFace, fontStyle, fontSize);
         sample.setFont(font);
         sample.repaint();
      }
   }
}
