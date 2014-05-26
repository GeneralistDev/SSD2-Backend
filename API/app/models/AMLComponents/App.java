package models.AMLComponents;

import org.stringtemplate.v4.ST;

/**
 * Created by danielparker on 26/05/2014.
 */
public class App {
    ST appTemplate = new ST(
        "app (<startScreenId>) {\n" +
        "   android-sdk: \"/Users/danielparker/Applications/android-sdk\"\n" +
        "}\n"
    );
    public String startScreenId = null;

    public App() {}

    public App(String startScreenId) {
        this.startScreenId = startScreenId;
    }

    public App(Screen startScreen) {
        this.startScreenId = startScreen.getScreenId();
    }

    public String toAMLString() {
        if (startScreenId != null) {
            appTemplate.add("startScreenId", startScreenId);
            return appTemplate.render();
        }
        else {
            return null;
        }
    }
}
