import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TransformerTest {

    @Test
    public void tranformSample1() throws Exception {
        SortingTransformer transformer = new SortingTransformer();
        String result = transformer.processXml("sample1.xml");
        assertEquals("<document><item><name>B</name><score>0.5</score></item>" +
                     "<item><name>A</name><score>0.8</score></item></document>", result);
    }

    @Test
    public void tranformSample2() throws Exception {
        SortingTransformer transformer = new SortingTransformer();
        String result = transformer.processXml("sample2.xml");
        assertEquals("<document><item><name>B</name><score>0.1</score></item>" +
                     "<item><name>A</name><score>0.2</score></item>" +
                     "<item><name>D</name><score>0.6</score></item>" +
                     "<item><name>C</name><score>0.9</score></item></document>", result);
    }

}
