package MeltWater.QuoteExtraction;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import MeltWater.QuoteExtractionV2.FoundText;

public class OpenNLPUtils {

	public String[] getSentences(String paragraph) throws InvalidFormatException, IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream("en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

		String[] sentences = sdetector.sentDetect(paragraph);
		is.close();
		return sentences;
	}

	private List<Integer> getTagIndex(String[] tags, String... tagsToGet) {
		List<String> tagsList = new ArrayList<String>(Arrays.asList(tagsToGet));
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < tags.length; i++) {
			if (tagsList.contains(tags[i])) {
				result.add(i);
			}
		}
		return result;
	}

	public String getPerson(String input, String modelName) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(modelName);
		POSModel model = new POSModel(is);
		POSTaggerME tagger = new POSTaggerME(model);

		ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));

		String line;

		String result = "";
		while ((line = lineStream.read()) != null) {

			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);

			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			// System.out.println(sample.toString());
			List<Integer> list = getTagIndex(sample.getTags(), "NNP");

			for (int i : list) {
				result += whitespaceTokenizerLine[i] + " ";
			}

		}
		lineStream.close();
		return result;
	}

	public List<FoundText> getPersonFromAll(String input, String modelName) throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(modelName);
		POSModel model = new POSModel(is);
		POSTaggerME tagger = new POSTaggerME(model);

		ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));

		String line;

		int startIndex = 0;

		List<FoundText> result = new ArrayList<FoundText>();

		while ((line = lineStream.read()) != null) {

			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);

			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			// System.out.println(sample.toString());
			List<Integer> list = getTagIndex(sample.getTags(), "NNP");

			for (int i = 0; i < whitespaceTokenizerLine.length; i++) {
				if (list.contains(i)) {
					result.add(new FoundText(whitespaceTokenizerLine[i], startIndex, startIndex + whitespaceTokenizerLine[i].length()));
				}
				startIndex += whitespaceTokenizerLine[i].length();

			}

			startIndex += line.length();

		}
		lineStream.close();
		return result;
	}
}
