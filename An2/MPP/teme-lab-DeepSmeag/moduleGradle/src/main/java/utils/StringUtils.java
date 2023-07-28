package utils;

import com.google.common.base.Splitter;
import com.google.common.html.HtmlEscapers;
public class StringUtils {
    public Iterable<String> splitter(String input) {
        Splitter splitter =  Splitter.on(",").trimResults().omitEmptyStrings();
        return splitter.splitToList(input);
    }
    public String unHtml(String input) {
        return HtmlEscapers.htmlEscaper().escape(input);
    }
}
