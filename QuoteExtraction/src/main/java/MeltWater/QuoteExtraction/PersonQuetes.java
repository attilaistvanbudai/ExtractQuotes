package MeltWater.QuoteExtraction;

public class PersonQuetes {

	private final String person;
	private final String quete;

	public PersonQuetes(String person, String quete) {
		this.person = person;
		this.quete = quete;

	}

	@Override
	public String toString() {
		return person + ": \"" + quete + "\"";
	}
}
