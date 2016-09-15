package MeltWater.QuoteExtractionV2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.language.LanguageIdentifier;

import MeltWater.QuoteExtraction.App;
import MeltWater.QuoteExtraction.FileReader;
import MeltWater.QuoteExtraction.OpenNLPUtils;
import MeltWater.QuoteExtraction.PersonQuetes;
import MeltWater.QuoteExtraction.PosModels;

/**
 * Hello world!
 *
 */
public class ExtractionAppVersionTwo {

	private FileReader reader;
	private OpenNLPUtils openNlpUtils;
	PosModels models;
	String other;

	public ExtractionAppVersionTwo() {
		reader = new FileReader();
		openNlpUtils = new OpenNLPUtils();
		models = new PosModels();
	}

	public void run(String fileName) throws IOException {

		String input = reader.getFileWithUtil(fileName).replace(System.getProperty("line.separator"), " ");

		LanguageIdentifier identifier = new LanguageIdentifier(input);
		String language = identifier.getLanguage();
		System.out.println(language);
		String posModelName = models.getModel(language);

		List<PersonQuetes> personQUetes = new ArrayList<>();
		List<FoundText> quetes = getQuotes(input);
		// for (FoundText quote : quetes) {
		// System.out.println(quote.getText());
		// }

		List<FoundText> persons = openNlpUtils.getPersonFromAll(input, posModelName);

		// for (FoundText person : persons) {
		// System.out.println(person.getText());
		// }

		pairUpQuoteWithPerson(quetes, persons);

	}

	public void pairUpQuoteWithPerson(List<FoundText> quetes, List<FoundText> persons) {
		int currentIndex = 0;
		for (FoundText quote : quetes) {

			int start = quote.getStart();
			int end = quote.getEnd();
			int minDistance = start;
			int minIndex = 0;
			for (; currentIndex < persons.size(); currentIndex++) {
				FoundText person = persons.get(currentIndex);
				int pStart = person.getStart();
				int pEnd = person.getEnd();
				if (pStart < start) {
					int min = pEnd - start;
					if (min < minDistance) {
						min = minDistance;
						minIndex = currentIndex;
					}

				}

				if (pStart > end) {

					int min = pStart - end;
					if (min < minDistance) {
						min = minDistance;
						minIndex = currentIndex;
					}

					System.out.print(persons.get(currentIndex).getText() + ": ");
					System.out.println(quote.getText());
					break;
				}

			}

		}

	}

	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		App app = new App();
		app.run("StoryExample");

	}

	public List<FoundText> getQuotes(String input) {
		Pattern p = Pattern.compile("\u201C(.*?)\u201D|\u201E(.*?)\u201C|\"(.*?)\"");
		Matcher m = p.matcher(input);
		List<FoundText> quetes = new ArrayList<FoundText>();
		other = "";
		int start = 0;
		while (m.find()) {
			quetes.add(new FoundText(m.group(), m.start(), m.end()));
		}
		other += input.substring(start);
		return quetes;
	}

	public Map<String, String> createMap() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("\u201C", "\u201D");
		map.put("\"", "\"");

		return map;

	}

}
