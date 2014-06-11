package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.AMLComponents.*;

import java.util.ArrayList;

/**
 * Created by danielparker on 26/05/2014.
 */
public class ModelTransformer {
    private ModelTransformer() {}

    public static String visualToAML(JsonNode rootNode, StringBuilder appName) throws ModelTransformationException{
        if (appName == null) {
            appName = new StringBuilder();
        }
        ArrayNode nodes = (ArrayNode)rootNode.path("nodes");
        /*ArrayNode links = (ArrayNode)rootNode.path("links");*/

        App app = new App();
        ArrayList<Screen> screens = new ArrayList<>();
        Navigation navigation = new Navigation();
        API api = new API();

        /* Check if there are any nodes */
        if (nodes.isMissingNode()) {
            throw new ModelTransformationException("Visual model is empty");
        }

        /* Iterate through all nodes and create screens */
        for (int i = 0; i < nodes.size(); i++) {
            JsonNode thisNode = nodes.get(i);
            String nodeType = thisNode.get("nodeType").asText();
            if (nodeType.equals("screenNode")) {
                Long id = thisNode.get("id").asLong();
                JsonNode attributes = thisNode.get("attributes");
                Screen screen = new Screen(id);

                if (id == null) {
                    throw new ModelTransformationException("Visual node is missing an ID");
                }

                if (attributes != null) {
                    String apiDomainString = null;
                    /**
                     * API RESOURCE
                     */
                    JsonNode apiDomain = attributes.get("apiDomain");
                    if (apiDomain != null && !apiDomain.asText().equals("")) {
                        apiDomainString = apiDomain.asText();
                        if (apiDomainString.charAt(apiDomainString.length() - 1) == '/') {
                            apiDomainString = apiDomainString.substring(0, apiDomainString.length() -2);
                        }
                    }

                    /**
                     * HAS TAB
                     */
                    JsonNode isTabBoolean = attributes.get("isTab");
                    if (isTabBoolean == null) {
                        throw new ModelTransformationException("The isTab boolean was null in the provided json");
                    }

                    Boolean isTab = isTabBoolean.asBoolean();


                    /**
                     * LAYOUT ITEMS
                     */
                    ArrayNode layoutItems = (ArrayNode)attributes.path("layoutItems");
                    if (!layoutItems.isMissingNode()) {
                        for (int lItem = 0; lItem < layoutItems.size(); lItem++) {
                            JsonNode item = layoutItems.get(lItem);
                            JsonNode viewType = item.get("viewType");
                            if (viewType.asText().equals("Label")) {
                                JsonNode labelValue = item.get("value");
                                screen.addLabel(labelValue.asText(), api, apiDomainString);
                            }
                        }
                    }

                    /**
                     * LANDING PAGE
                     */
                    JsonNode startScreenBoolean = attributes.get("isLanding");
                    if (startScreenBoolean == null) {
                        throw new ModelTransformationException("Start screen boolean was null in json");
                    }

                    Boolean isStartScreen = startScreenBoolean.asBoolean();

                    if (isStartScreen != null) {
                        if (isStartScreen) {
                            app.startScreenId = screen.getScreenId();
                        }

                        if (isTab) {
                            JsonNode tabLabel = attributes.get("tabLabel");
                            navigation.addTab(tabLabel.asText(), screen.getScreenId());
                        }
                        screens.add(screen);
                    }
                } else {
                    throw new ModelTransformationException("Node is missing attributes");
                }
            } else if (nodeType.equals("appPropertiesNode")) {
                JsonNode attributes = thisNode.get("attributes");
                if (attributes != null) {
                    JsonNode navType = attributes.get("navigationType");
                    JsonNode applicationName = attributes.get("appName");
                    if (applicationName == null) {
                        throw new ModelTransformationException("No application name was supplied");
                    }
                    appName.append(applicationName.asText());

                    switch (navType.asText()) {
                        case "Drawer":
                            navigation.setNavType(Navigation.NavType.DRAWER);
                            break;
                        case "Tabbar":
                            navigation.setNavType(Navigation.NavType.TABBAR);
                            break;
                    }
                } else {
                    throw new ModelTransformationException("App properties has no attributes");
                }
            }
        }

        /* Construct the final AML string and return it */
        StringBuilder AML = new StringBuilder();
        app.addOptional(navigation.toAMLString());
        String appString = app.toAMLString();
        if (appString == null) {
            throw new ModelTransformationException("App properties had no id");
        } else {
            AML.append(appString);
            AML.append(api.toAMLString());
            for (Screen s : screens) {
                AML.append(s.toAMLString());
            }
            System.out.println(AML.toString());
            return AML.toString();
        }
    }
}
