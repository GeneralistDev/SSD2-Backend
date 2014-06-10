package models.AMLComponents;

import org.stringtemplate.v4.ST;

/**
 * @author Daniel Parker <a href="mailto:dparker.tech@gmail.com">dparker.tech@gmail.com</a>
 */
public class API {
    ST apiTemplateStart = new ST(
            "api appAPI \"<APIUrl>\" {\n"
    );

    ST resourceTemplate = new ST(
            "\tresource <resourceName> GET \"<endPointUrl>\"\n"
    );

    ST apiTemplateEnd = new ST(
            "}\n"
    );

    public API() {

    }

    public void addAPIUrl(String APIUrl) {
        apiTemplateStart.add("APIUrl", APIUrl);
    }

    /**
     * This should be changed to allow for different endpoints
     * @param resourceName  The name of the resource
     */
    public void addResource(String resourceName, String endPoint) {
        resourceTemplate.add("resourceName", resourceName);
        resourceTemplate.add("endPointUrl", endPoint);
    }

    public String toAMLString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiTemplateStart.render());
        stringBuilder.append(resourceTemplate.render());
        stringBuilder.append(apiTemplateEnd.render());

        return stringBuilder.toString();
    }
}
