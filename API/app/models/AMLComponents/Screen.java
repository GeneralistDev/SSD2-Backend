package models.AMLComponents;

import org.stringtemplate.v4.ST;

/**
 * Created by danielparker on 26/05/2014.
 */
public class Screen {
    private long id;
    private String screenId;
    private ST screenTemplate = new ST(
        "screen <id> \"<screenName>\" {\n" +
        "   view <layoutID> {\n" +
        "       label <label> \"Screen <screenName>\"\n" +
        "   }\n" +
        "}\n"
    );

    public Screen(long id) {
        this.id = id;
        this.screenId = "Screen" + id;
    }

    public String getScreenId() {
        return screenId;
    }

    public String toAMLString() {
        screenTemplate.add("id", screenId);
        screenTemplate.add("screenName", screenId);
        screenTemplate.add("label", screenId + "label");
        screenTemplate.add("layoutID", screenId + "layout");
        return screenTemplate.render();
    }
}
