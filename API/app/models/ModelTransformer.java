package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.AMLComponents.App;
import models.AMLComponents.Screen;

import java.util.ArrayList;

/**
 * Created by danielparker on 26/05/2014.
 */
public class ModelTransformer {


    private ModelTransformer() {}

    public static String visualToAML(JsonNode rootNode) {
        ArrayNode nodes = (ArrayNode)rootNode.path("nodes");
        /*ArrayNode links = (ArrayNode)rootNode.path("links");*/

        App app = new App();
        ArrayList<Screen> screens = new ArrayList<>();

        /* Check if there are any nodes */
        if (nodes.isMissingNode()) {
            return null;
        }

        /* Iterate through all nodes and create screens */
        for (int i = 0; i < nodes.size(); i++) {
            JsonNode thisNode = nodes.get(i);
            String nodeType = thisNode.get("nodeType").asText();
            if (nodeType.equals("screenNode")) {
                Long id = thisNode.get("nodeID").asLong();
                JsonNode attributes = thisNode.get("attributes");

                if (id == null) {
                    return null;
                }

                if (attributes != null) {
                    JsonNode startScreenBoolean = attributes.get("isLanding");
                    if (startScreenBoolean == null) {
                        return null;
                    }

                    Boolean isStartScreen = startScreenBoolean.asBoolean();

                    if (isStartScreen != null) {
                        Screen screen = new Screen(id);
                        if (isStartScreen) {
                            app.startScreenId = screen.getScreenId();
                        }
                        screens.add(screen);
                    }
                } else {
                    return null;
                }
            }
        }

        /* Construct the final AML string and return it */
        StringBuilder AML = new StringBuilder();
        String appString = app.toAMLString();
        if (appString == null) {
            return null;
        } else {
            AML.append(appString);
            for (Screen s : screens) {
                AML.append(s.toAMLString());
            }
            return AML.toString();
        }
    }
}
