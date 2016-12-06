package collections;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service("injectCollection")
public class CollectionInjection {
	@Resource(name="map")
	private Map<String, Object> map;
	@Resource(name="props")
	private Properties props;
	@Resource(name="set")
	private Set set;
	@Resource(name="list")
	private List list;
	
	public static void main(String [] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:collections/app-context-annotation.xml");
		context.refresh();
		
		CollectionInjection instance = (CollectionInjection) context.getBean("injectCollection");
		instance.displayInfo();
	}

	/*public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public void setList(List list) {
		this.list = list;
	}*/
	
	public void displayInfo() {
		System.out.println("Map contents:\n");
		for (Map.Entry<String, Object> entry: map.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
		}
		System.out.println("\nProperties contexts:\n");
		for (Map.Entry<Object, Object> entry: props.entrySet()) {
			System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
		}
		System.out.println("\nSet contents:\n");
		for (Object obj : set) {
			System.out.println("Value: " + obj);
		}
		System.out.println("\nList contexts:\n");
		for (Object obj : list) {
			System.out.println("Value: " + obj);
		}
	}
}