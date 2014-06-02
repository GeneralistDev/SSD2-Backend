package models.AMLComponents;

import org.stringtemplate.v4.ST;

/**
 * Created by danielparker on 26/05/2014.
 */
public class App {
    ST appTemplateStart = new ST(
        "app (<startScreenId>) {\n" +
        "   android-sdk: \"/Users/danielparker/Applications/android-sdk\"\n"
    );

    ST appTemplateEnd = new ST (
            "}\n"
    );

    StringBuilder optionals = new StringBuilder();
    public String startScreenId = null;

    public App() {}

    public App(Screen startScreen) {
        this.startScreenId = startScreen.getScreenId();
    }

    public void addOptional(String s) {
        optionals.append(s);
    }

    public String toAMLString() {
        StringBuilder resultString = new StringBuilder();
        appTemplateStart.add("startScreenId", startScreenId);
        resultString.append(appTemplateStart.render());
        resultString.append(optionals.toString());
        resultString.append(appTemplateEnd.render());
        return resultString.toString();
    }
}
