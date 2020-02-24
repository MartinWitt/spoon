package spoon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

	// following test cases showcase use cases
	@Test
	public void testSimpleUsage() throws IOException {
		CtModel model = new FluentLauncher().inputResource("src/test/resources/deprecated/input")
				.noClasspath(true)
				.outputDirectory(folderFactory.newFolder().getPath())
				.buildModel();
		assertNotNull(model);
	}

	@Test
	public void testProcessor() throws IOException {
		List<String> output = new ArrayList<>();
		List<String> output2 = new ArrayList<>();
		new FluentLauncher().inputResource("src/test/resources/deprecated/input")
				.outputDirectory(folderFactory.newFolder().getPath())
				.processor(List.of(new AbstractProcessor<CtType<?>>() {
					public void process(CtType<?> element) {
						output.add(element.toString());
					}
				}))
				.processor(new AbstractProcessor<CtType<?>>() {
					public void process(CtType<?> element) {
						output2.add(element.toString());
					}
				})
				.noClasspath(true)
				.buildModel();
		// shouldn't be empty after processor usage.
		assertTrue(!output.isEmpty());
		// both lists should be same because same processor type
		assertEquals(output, output2);
	}

	@Test
	public void testConsistency() throws IOException {
		new FluentLauncher().inputResource("src/test/resources/deprecated/input")
				.noClasspath(true)
				.complianceLevel(11)
				.disableConsistencyChecks()
				.outputDirectory(folderFactory.newFolder().getPath())
				.buildModel()
				.getUnnamedModule()
				.accept(new AstParentConsistencyChecker());
	}

	@Test
	public void testSettings() throws IOException {
		CtModel model = new FluentLauncher().inputResource("src/test/resources/deprecated/input")
				.noClasspath(true)
				.encoding(Charset.defaultCharset())
				.autoImports(true)
				.outputDirectory(folderFactory.newFolder())
				.buildModel();
		assertNotNull(model);
	}

	/**
	 * shows using the FluentLauncher with different launchers.
	 *
	 * @throws IOException
	 */
	@Test
	public void testMavenLauncher() throws IOException {
		CtModel model = new FluentLauncher(new MavenLauncher("./pom.xml", MavenLauncher.SOURCE_TYPE.ALL_SOURCE))
				.outputDirectory(folderFactory.newFolder().getPath())
				.buildModel();
		assertNotNull(model);
	}
}
