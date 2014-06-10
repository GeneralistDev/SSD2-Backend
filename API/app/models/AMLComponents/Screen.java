package models.AMLComponents;

import org.stringtemplate.v4.ST;

import java.util.LinkedHashMap;

/**
 * Created by danielparker on 26/05/2014.
 */
public class Screen {
    private long id;
    private String screenId;
    private LinkedHashMap<String, String> labels = new LinkedHashMap<>();
    private Boolean needsOnLoad = false;

    private ST screenTemplateStart = new ST(
        "screen <id> \"<screenName>\" {\n" +
        "   view <layoutID> {\n"
    );

    private ST onLoadStart = new ST (
        "       on-load {\n"
    );

    private ST callTemplate = new ST (
        "           call <apiResource>\n"
    );

    private ST onLoadEnd = new ST (
        "       }\n"
    );

    private ST labelTemplate = new ST(
        "       label <labelID> \"<labelValue>\"\n"
    );

    private ST APILabelTemplate = new ST(
        "       label <labelID>\n"
    );

    private ST screenTemplateEnd = new ST(
        "   }\n" +
        "}\n"
    );

    /**
     * Very messy adding of a label, needs refactoring.
     */
    public void addLabel(String value, API api, String domain) {
        String APIPart = value.substring(0, 3);
        if (APIPart.equals("API")) {
            String rest = value.substring(3);
            String[] parts = rest.split(":");
            if (!needsOnLoad) {
                domain = domain + parts[0];
                api.addAPIUrl(domain);
                api.addResource(screenId + "Res", "/" + screenId);
                callTemplate.add("apiResource", "appAPI." + screenId + "Res");
                needsOnLoad = true;
            }
            String[] jsonNav = parts[1].split("\\.");
            StringBuilder labelValue = new StringBuilder();
            for (int i = 0; i < jsonNav.length; i++) {
                if ((jsonNav.length - 1) == i) {
                    if (i == 0) {
                        labelValue.append(jsonNav[0] + "<stringT>");
                    } else {
                        labelValue.append("." + jsonNav[i] + "<stringT>");
                    }
                } else if (i != 0) {
                    labelValue.append(".");
                    labelValue.append(jsonNav[i] + "<objectT>");
                } else {
                    labelValue.append(jsonNav[i] + "<objectT>");
                }
            }
            labels.put(labelValue.toString(), null);
        } else {
            labels.put(value, value);
        }
    }

    public Screen(long id) {
        this.id = id;
        this.screenId = "Screen" + id;
    }

    public String getScreenId() {
        return screenId;
    }

    public String toAMLString() {
        StringBuilder stringBuilder = new StringBuilder();
        screenTemplateStart.add("id", screenId);
        screenTemplateStart.add("screenName", screenId);
        screenTemplateStart.add("layoutID", screenId + "Layout");
        stringBuilder.append(screenTemplateStart.render());
        if (needsOnLoad) {
            stringBuilder.append(onLoadStart.render());
            stringBuilder.append( callTemplate.render());
            stringBuilder.append(onLoadEnd.render());
        }
        for (String key: labels.keySet()) {
            if (labels.get(key) != null) {
                labelTemplate.add("labelID", labels.get(key));
                labelTemplate.add("labelValue", labels.get(key));
                stringBuilder.append(labelTemplate.render());
                labelTemplate.remove("labelID");
                labelTemplate.remove("labelValue");
            } else {
                APILabelTemplate.add("labelID", key);
                stringBuilder.append(APILabelTemplate.render());
                APILabelTemplate.remove("labelID");
            }
        }
        stringBuilder.append(screenTemplateEnd.render());
        return stringBuilder.toString();
    }
}
