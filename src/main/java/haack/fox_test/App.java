package haack.fox_test;

import haack.fox_test.extractor.AbstractExtractor;
import haack.fox_test.extractor.Spotlight.ExtractorSpotlight;
import haack.fox_test.extractor.fox.ExtractorFox;

public class App 
{
    public static void main( String[] args )
    {
        AbstractExtractor ex = new ExtractorFox();
        String input = "The philosopher and mathematician Leibniz was born in Leipzig in 1646 and attended the University of Leipzig from 1661-1666.";
        String output = ex.extract(input);
        System.out.println(output);
    }
}
