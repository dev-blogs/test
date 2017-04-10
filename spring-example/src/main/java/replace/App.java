package replace;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.StopWatch;

public class App {
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:replace/replacement.xml");
		context.refresh();
		
		// 249 20 job's machine
		// 259 20 job's mac machine
		// 285 25 kotik's machine
		// 159 12 my machine
		
		ReplacementTarget replacementTarget = (ReplacementTarget) context.getBean("replacementTarget");
		ReplacementTarget standardTarget = (ReplacementTarget) context.getBean("standardTarget");
		
		displayInfo(replacementTarget);
		displayInfo(standardTarget);
	}
	
	private static void displayInfo(ReplacementTarget target) {
        System.out.println(target.formatMessage("Hello World!"));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("perfTest");

        for (int x = 0; x < 1000000; x++) {
            String out = target.formatMessage("foo");
        }

        stopWatch.stop();

        System.out.println("1000000 invocations took: "
                + stopWatch.getTotalTimeMillis() + " ms");
    }
}