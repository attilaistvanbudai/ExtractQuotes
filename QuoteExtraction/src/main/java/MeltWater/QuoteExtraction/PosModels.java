package MeltWater.QuoteExtraction;

import java.util.HashMap;
import java.util.Map;

public class PosModels {

	static Map<String, String> modelMap = new HashMap<String, String>();

	{
		modelMap.put("en", "en-pos-maxent.bin");
		modelMap.put("fr", "fr-pos-ftb-morpho.bin");
		modelMap.put("de", "de-pos-perceptron.bin");
	}

	public PosModels() {

	}

	public String getModel(String langugage) {
		if (modelMap.get(langugage) != null) {
			return modelMap.get(langugage);
		} else {
			return modelMap.get("en");
		}

	}

}
