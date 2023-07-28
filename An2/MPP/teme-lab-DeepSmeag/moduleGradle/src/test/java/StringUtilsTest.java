import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.StringUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {
    StringUtils stringUtils = new StringUtils();

    @Test
    @DisplayName("Test Splitter")
    public void testSplitter() {
        assertEquals(Arrays.asList("a", "b", "c"), stringUtils.splitter("  , ,a,b,c, "));

    }
    @Test
    @DisplayName("Test UnHtml")
    public void testUnHtml() {
        assertEquals("&lt;a href=&quot;http://www.google.com&quot;&gt;Google&lt;/a&gt;", stringUtils.unHtml("<a href=\"http://www.google.com\">Google</a>"));
    }
}
