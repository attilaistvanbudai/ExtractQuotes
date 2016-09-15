package MeltWater.QuoteExtraction;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class FileReader {

	public String getFileWithUtil(String fileName) {

		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			String en = guessEncoding(IOUtils.toByteArray(classLoader.getResourceAsStream(fileName)));
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName), Charset.forName(en));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String guessEncoding(byte[] bytes) {
		String DEFAULT_ENCODING = "UTF-8";
		org.mozilla.universalchardet.UniversalDetector detector = new org.mozilla.universalchardet.UniversalDetector(null);
		detector.handleData(bytes, 0, bytes.length);
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
		}
		return encoding;
	}

}
