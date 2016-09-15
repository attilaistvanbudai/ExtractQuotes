package MeltWater.QuoteExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.language.LanguageIdentifier;

/**
 * Hello world!
 *
 */
public class App {

	private FileReader reader;
	private OpenNLPUtils openNlpUtils;
	PosModels models;
	String other;

	public App() {
		reader = new FileReader();
		openNlpUtils = new OpenNLPUtils();
		models = new PosModels();
	}

	public void run(String fileName) throws IOException {

		String input = reader.getFileWithUtil(fileName).replace(System.getProperty("line.separator"), " ");

		LanguageIdentifier identifier = new LanguageIdentifier(input);
		String language = identifier.getLanguage();
		String posModelName = models.getModel(language);

		List<PersonQuetes> personQUetes = new ArrayList<>();
		for (String s : openNlpUtils.getSentences(input)) {
			List<String> quetes = getQuotes(s);
			String person = openNlpUtils.getPerson(other, posModelName);
			if (!quetes.isEmpty()) {
				String per = person;
				if (person.isEmpty()) {
					per = "???";
				}
				for (String q : quetes) {

					personQUetes.add(new PersonQuetes(per, q));
				}
			}
		}

		for (PersonQuetes pq : personQUetes) {
			System.out.println(pq);
		}

	}

	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		App app = new App();
		app.run("StoryExample");

	}

	public List<String> getQuotes(String input) {
		Pattern p = Pattern.compile("\u201C(.*?)\u201D|\u201E(.*?)\u201C|\"(.*?)\"");
		Matcher m = p.matcher(input);
		List<String> quetes = new ArrayList<String>();
		other = "";
		int start = 0;
		while (m.find()) {
			quetes.add(m.group());
			other += input.substring(start, m.start());
			start = m.end();
		}
		other += input.substring(start);
		return quetes;
	}

}
