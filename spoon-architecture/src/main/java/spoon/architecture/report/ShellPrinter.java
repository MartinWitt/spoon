/**
 * SPDX-License-Identifier:  MIT
 */
package spoon.architecture.report;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * This defines a report printer. As output is the shell or system.out used.
 * The printer has time measurement included, for time in method and time for all methods.
 */
public class ShellPrinter implements IReportPrinter {

	private Instant start;
	private Instant finish;
	private Instant methodStart;
	private Instant methodFinish;
	@Override
	public void afterMethod(Method method) {
		methodFinish = Instant.now();
		System.out.println("Finished check " + method.getName() + " in " + TimeUnit.MILLISECONDS.toSeconds(Duration.between(methodStart, methodFinish).toMillis()) + " seconds");
	}

	@Override
	public void beforeMethod(Method method) {
		System.out.println("Running check: " + method.getName());
		methodStart = Instant.now();
	}

	@Override
	public void finishPrinting() {
		finish = Instant.now();
		System.out.println("Finished running architecture checks in " + TimeUnit.MILLISECONDS.toSeconds(Duration.between(start, finish).toMillis()) + " seconds");
	}

	@Override
	public void startPrinting() {
		start = Instant.now();
		System.out.println("Starting running architecture checks");
	}

}
