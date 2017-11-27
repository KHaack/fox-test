package haack.fox_test.extractor.fox;

import java.net.URL;

import org.aksw.fox.binding.java.FoxApi;
import org.aksw.fox.binding.java.IFoxApi;
import org.aksw.fox.binding.java.FoxParameter.FOXLIGHT;
import org.aksw.fox.binding.java.FoxParameter;
import org.aksw.fox.binding.java.FoxResponse;

import haack.fox_test.extractor.AbstractExtractor;

/**
 * FOX uses AGDISTIS, an Open Source Named Entity Disambiguation Framework 
 * http://aksw.org/Projects/FOX.html
 * https://github.com/dice-group/FOX
 * https://hub.docker.com/r/rpietzsch/fox-docker/
 * @author Kevin Haack
 *
 */
public class ExtractorFox extends AbstractExtractor {

	@Override
	public String extract(String toExtract) {
		try {
		IFoxApi fox = new FoxApi();

		URL api = new URL("http://fox-demo.aksw.org/fox");
		//URL api = new URL("http://fox-demo.aksw.org:4444/fox");
		//URL api = new URL("http://139.18.2.164:4444/api");
		//URL api = new URL("http://127.0.0.1:4444/api");
		fox.setApiURL(api);

		fox.setLightVersion(FOXLIGHT.OFF);
		fox.setTask(FoxParameter.TASK.NER);
		fox.setOutputFormat(FoxParameter.OUTPUT.TURTLE);
		fox.setLang(FoxParameter.LANG.EN);
		
		// fox.setInput(new URL("https://en.wikipedia.org/wiki/Leipzig_University"));
		fox.setInput(toExtract);
		
		FoxResponse response = fox.send();
		
		return response.getOutput();
		} catch(Exception ex) {
			throw new RuntimeException("Unable to extract. ", ex);
		}
	}
}
