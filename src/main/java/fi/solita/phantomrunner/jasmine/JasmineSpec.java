package fi.solita.phantomrunner.jasmine;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import fi.solita.phantomrunner.PhantomProcess;
import fi.solita.phantomrunner.testinterpreter.JavascriptTest;
import fi.solita.phantomrunner.util.ObjectMemoizer;
import fi.solita.phantomrunner.util.ParametrizedFactory;
import fi.solita.phantomrunner.util.Strings;

public class JasmineSpec implements JavascriptTest {

	private final String name;
	private final String testData;
	private final JasmineSuite parent;
	private final ObjectMemoizer<Description, Class<?>> description;
	
	public JasmineSpec(String testData, JasmineSuite parent, Class<?> parentTestClass) {
		this.name = Strings.firstMatch(testData, "(?<=\").*(?=\")");
		this.testData = testData;
		this.parent = parent;
		
		this.description = new ObjectMemoizer<Description, Class<?>>(
				new ParametrizedFactory<Description, Class<?>>() {
					
			@Override
			public Description create(Class<?> param) {
				return Description.createTestDescription(param, getTestName());
			}
		}, parentTestClass);
	}
	
	@Override
	public Description asDescription(Class<?> parentTestClass) {
		return description.get();
	}

	@Override
	public void run(RunNotifier notifier, PhantomProcess process) {
		notifier.fireTestStarted(description.get());
		try {
			process.runTest(this);
			notifier.fireTestFinished(description.get());
		} catch (Throwable t) {
			notifier.fireTestFailure(new Failure(description.get(), t));
		}
	}
	
	@Override
	public String getTestName() {
		return name;
	}

	@Override
	public String getTestData() {
		return testData;
	}
	
	@Override
	public String toString() {
		return testData;
	}

	@Override
	public boolean isTest() {
		return true;
	}

	@Override
	public String getSuiteName() {
		return parent.getSuiteName();
	}
}