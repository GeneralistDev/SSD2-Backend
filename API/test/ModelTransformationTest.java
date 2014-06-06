import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ModelTransformer;
import org.junit.Test;
import static org.fest.assertions.Assertions.*;

import java.io.IOException;

/**
 * Created by danielparker on 26/05/2014.
 */
public class ModelTransformationTest {

    @Test
    public void appAndScreens() {
        String jsonString = "{\n" +
                "\t\"nodes\": [\n" +
                "        {\n" +
                "            \"nodeID\": 1,\n" +
                "            \"nodeType\": \"appProperties\",\n" +
                "            \"attributes\": {\n" +
                "                \"navigationType\": \"Drawer\",\n" +
                "                \"appName\": \"My Book List\",\n" +
                "                \"icon\": \"default\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "\t        \"nodeID\": 2,\n" +
                "\t        \"nodeType\": \"screenNode\",\n" +
                "\t        \"attributes\": {\n" +
                "\t            \"isLanding\": true,\n" +
                "\t            \"isTab\": true,\n" +
                "\t            \"tabLabel\": \"Home\",\n" +
                "\t            \"screenLabel\": \"Home Screen\",\n" +
                "\t            \"name\": \"Home\",\n" +
                "\t            \"layoutItems\": [\n" +
                "\t                {\n" +
                "\t                    \"viewType\": \"button\",\n" +
                "\t                    \"value\": \"view list\"\n" +
                "\t                },\n" +
                "\t                {\n" +
                "\t                    \"viewType\": \"label\",\n" +
                "\t                    \"value\": \"welcome!\"\n" +
                "\t                }\n" +
                "\t            ]\n" +
                "            }\n" +
                "    \t},\n" +
                "\t\t{\n" +
                "\t        \"nodeID\": 3,\n" +
                "\t        \"nodeType\": \"screenNode\",\n" +
                "\t        \"attributes\": {\n" +
                "\t            \"isLanding\": false,\n" +
                "\t            \"isTab\": true,\n" +
                "\t            \"tabLabel\": \"List\",\n" +
                "\t            \"screenLabel\": \"Book List\",\n" +
                "\t\t\t\t\"name\" : \"BookList\",\n" +
                "\t\t\t\t\"apiDomain\" : \"http: //booklist-api.com/api/v1/\",\n" +
                "\t\t\t\t\"layoutItems\" : [{\n" +
                "\t\t\t\t\t\"viewType\" : \"list\",\n" +
                "\t\t\t\t\t\"value\" : \"GET/booklist\"\n" +
                "\t\t\t\t}]\n" +
                "\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{ \n" +
                "\t\t\t\"nodeID\": 4,\n" +
                "\t\t\t\"nodeType\" : \"screenNode\",\n" +
                "\t\t\t\"attributes\" : {\n" +
                "\t\t\t\t\"isLanding\" : false,\n" +
                "\t\t\t\t\"isTab\" : false,\n" +
                "\t\t\t\t\"tabLabel\" : \"\",\n" +
                "\t\t\t\t\"screenLabel\" : \"BookDetails\",\n" +
                "\t\t\t\t\"name\" : \"BookDetails\",\n" +
                "\t\t\t\t\"layoutItems\" : [{\n" +
                "\t\t\t\t\t\"viewType\" : \"label\",\n" +
                "\t\t\t\t\t\"value\" : \"bookdetailsscreen\"\n" +
                "\t\t\t\t}]\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"links\" : [{\n" +
                "\t\t\"linkID\" : 1,\n" +
                "\t\t\"linkType\" : \"screenTransitionLink\",\n" +
                "\t\t\"attributes\" : {\n" +
                "\t\t\t\"condition\" : \"onButtonClick\"\n" +
                "\t\t},\n" +
                "\t\t\"sourceID\" : 2,\n" +
                "\t\t\"targetID\" : 3  \n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"linkID\" : 2,\n" +
                "\t\t\"linkType\" : \"screenTransitionLink\",\n" +
                "\t\t\"attributes\" : {\n" +
                "\t\t\t\"condition\" : \"onListItemClick\"\n" +
                "\t\t},\n" +
                "\t\t\"sourceID\" : 3,\n" +
                "\t\t\"targetID\" : 4\n" +
                "\t}]\n" +
                "}\n";

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(jsonString);
            String AMLString = ModelTransformer.visualToAML(json);
            if (AMLString != null) {
                System.out.println(AMLString);
                assert(true);
            } else {
                assertThat(AMLString).isNotNull();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
