package com.devblogs.lookup;

public abstract class AbstractLookupDemoBean implements DemoBean {

	public abstract MyHelper getMyHelper();

	public void someOperation() {
		getMyHelper().doSomethingHelpful();
	}
}