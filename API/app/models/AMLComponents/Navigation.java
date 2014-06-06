package models.AMLComponents;

import org.stringtemplate.v4.ST;
import java.util.HashMap;

/**
 * @author Daniel Parker <a href="mailto:dparker.tech@gmail.com">dparker.tech@gmail.com</a>
 */
public class Navigation {
    HashMap<String, String> titleToScreenIdList = new HashMap<>();

    public enum NavType {
        TABBAR,
        DRAWER
    }

    ST navTemplateStart = new ST(
            "\tnavigation <NavType> navId {\n"
    );

    ST tabTemplate = new ST(
            "\t\ttab <tabId> \"<tabTitle>\" to <screenId>\n"
    );

    ST navTemplateEnd = new ST(
            "\t}\n"
    );

    public Navigation() { }

    public void addTab(String title, String screenId) {
        titleToScreenIdList.put(title, screenId);
    }

    public void setNavType(NavType nT) {
        switch (nT) {
            case TABBAR:
                navTemplateStart.add("NavType", "Tabbar");
                break;
            case DRAWER:
                navTemplateStart.add("NavType", "Drawer");
                break;
        }
    }

    public String toAMLString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(navTemplateStart.render());

        for (String key: titleToScreenIdList.keySet()) {
            String tabId = titleToScreenIdList.get(key) + "TabId";
            tabTemplate.add("tabId", tabId);
            tabTemplate.add("tabTitle", key);
            tabTemplate.add("screenId", titleToScreenIdList.get(key));
            stringBuilder.append(tabTemplate.render());
            tabTemplate.remove("tabId");
            tabTemplate.remove("tabTitle");
            tabTemplate.remove("screenId");
        }

        stringBuilder.append(navTemplateEnd.render());
        return stringBuilder.toString();
    }
}
