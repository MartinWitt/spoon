package spoon;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import spoon.processing.AbstractProcessor;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.AstParentConsistencyChecker;

/**
 * FluentLauncherTest
 */
public class FluentLauncherTest {
	@Rule
	public TemporaryFolder folderFactory = new TemporaryFolder();

	// following testcases showcase usecases
	public void useCase1() throws IOException {
		CtModel model = new FluentLauncher().inputResource("src/test/resources/deprecated/input").noClasspath(true)
				.outputDirectory(folderFactory.newFolder().getPath()).buildModel();
	}

	@Test
	public void useCase2() throws IOException {
		new FluentLauncher().inputResource("src/test/resources/deprecated/input")
				.outputDirectory(folderFactory.newFolder().getPath()).processor(new AbstractProcessor<CtType<?>>() {
					public void process(CtType<?> element) {
						System.out.println(element.toString());
					}
				}).noClasspath(true).buildModel();
	}

	@Test
	public void useCase3() throws IOException {
		new FluentLauncher().inputResource("src/test/resources/deprecated/input").noClasspath(true)
				.outputDirectory(folderFactory.newFolder().getPath()).buildModel().getUnnamedModule()
				.accept(new AstParentConsistencyChecker());
	}
}
