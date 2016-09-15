package MeltWater.QuoteExtraction;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class OpenNLPUtilsTest {

	OpenNLPUtils utils;

	@Before
	public void setup() {
		utils = new OpenNLPUtils();
	}

	@Test
	public void test() throws IOException {
		String posModelName = "en-pos-maxent.bin";
		String person = utils.getPerson("John walks in the bar", posModelName);
		Assert.assertEquals("John ", person);

	}
}
