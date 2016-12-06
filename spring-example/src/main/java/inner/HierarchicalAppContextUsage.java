package inner;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.devblogs.MessageProvider;
import com.devblogs.MessageRenderer;

public class HierarchicalAppContextUsage {
	public static void main(String [] args) {
		GenericXmlApplicationContext parent = new GenericXmlApplicationContext();
		parent.load("classpath:inner/parent.xml");
		parent.refresh();
		
		GenericXmlApplicationContext child = new GenericXmlApplicationContext();
		child.load("classpath:inner/child.xml");
		child.setParent(parent);
		child.refresh();
		
		//MessageProvider bean2 = child.getBean("messageProvider", MessageProvider.class);
		//System.out.println(bean2.getMessage());
		
		MessageRenderer bean = child.getBean("messageRenderer", MessageRenderer.class);
		bean.render();
	}
}