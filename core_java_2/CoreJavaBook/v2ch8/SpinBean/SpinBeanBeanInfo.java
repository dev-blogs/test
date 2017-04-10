/**
   @version 1.10 1997-10-27
   @author Cay Horstmann
*/

import java.awt.*;
import java.beans.*;

/**
   The bean info for the chart bean, specifying the 
   customizer.
*/
public class SpinBeanBeanInfo extends SimpleBeanInfo
{  
   public BeanDescriptor getBeanDescriptor()
   {  
      return new BeanDescriptor(SpinBean.class,
         SpinBeanCustomizer.class);
   }
}
