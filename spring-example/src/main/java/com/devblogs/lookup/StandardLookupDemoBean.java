package com.devblogs.lookup;

public class StandardLookupDemoBean implements DemoBean {
	private MyHelper myHelper;
	
	public void setMyHelper(MyHelper myHelper) {
		this.myHelper = myHelper;
	}
	
	public MyHelper getMyHelper() {
		return this.myHelper;
	}

	public void someOperation() {
		myHelper.doSomethingHelpful();
	}
}