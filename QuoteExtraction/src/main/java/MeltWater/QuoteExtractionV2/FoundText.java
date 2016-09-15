package MeltWater.QuoteExtractionV2;

public class FoundText {

	private final int start;
	private final int end;
	private final String text;

	public FoundText(String text, int start, int end) {
		this.start = start;
		this.end = end;
		this.text = text;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getText() {
		return text;
	}

}
