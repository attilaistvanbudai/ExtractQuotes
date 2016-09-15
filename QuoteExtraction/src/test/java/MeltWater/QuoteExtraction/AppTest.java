package MeltWater.QuoteExtraction;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

	App app;

	@Before
	public void setup() {
		app = new App();

	}

	@Test
	public void test0() throws IOException {
		app.run("StoryExample");
	}

	@Test
	public void test1() throws IOException {
		System.out.println("quote detection article1.txt");
		app.run("quote detection article1.txt");
	}

	@Test
	public void test2() throws IOException {
		System.out.println("quote detection article2.txt");
		app.run("quote detection article2.txt");
	}

	@Test
	public void test3() throws IOException {
		System.out.println("quote detection article3.txt");
		app.run("quote detection article3.txt");
	}

	@Test
	public void test4() throws IOException {
		System.out.println("quote detection article4.txt");
		app.run("quote detection article4.txt");
	}

	@Test
	public void test5() throws IOException {
		System.out.println("quote detection article5.txt");
		app.run("quote detection article5.txt");

		System.out.println(app.getQuotes("quote detection article5.txt"));
	}

	@Test
	public void testGetQuetes() throws IOException {
		System.out.println("quote detection article5.txt");
		FileReader reader = new FileReader();
		String input = reader.getFileWithUtil("quote detection article5.txt").replace(System.getProperty("line.separator"), " ");
		System.out.println(app.getQuotes(input));
	}

	@Test
	public void testGetQuetes2() throws IOException {
		FileReader reader = new FileReader();
		String input = reader.getFileWithUtil("StoryExample").replace(System.getProperty("line.separator"), " ");
		for (String s : app.getQuotes(input)) {
			System.out.println(s);
		}
	}

}
