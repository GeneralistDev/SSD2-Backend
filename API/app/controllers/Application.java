package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import play.*;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import models.*;

import views.html.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    /**
    * Preflight method that sets required http response header so that CORS enabled clients
    * will not fail to request resources from another domain. 
    **/
    public static Result checkPreFlight(String all) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
	response().setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
	response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
	return ok();
}
    public static Result newVisModel() {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	RequestBody body = request().body();
    	VisualModel vModel = new VisualModel();
    	JsonNode json = body.asJson();
    	if (json != null) {
    		vModel.jsonModel = json.toString();
    	} else {
    		ObjectNode empty = Json.newObject();
    		vModel.jsonModel = empty.toString();
    	}
    	vModel.save();				// Save object to database

    	ObjectNode result = Json.newObject();	// JSON to return
    	result.put("id", vModel.id.toString());	// add id to result json
 	return created(result);			// return 201 created with id in json body
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateVisModel(Long id) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	RequestBody body = request().body();
    	VisualModel vModel = VisualModel.find.where().eq("Id", id).findUnique();
    	if (vModel == null) {
    		return notFound();
    	}
    	JsonNode json = body.asJson();
    	if (json == null) {
    		return status(406);
    	}
    	vModel.jsonModel = json.toString();
    	vModel.update(id);
    	Logger.info(body.asText());
    	return ok(
    		body.asJson()
    	);
    }

    public static Result getVisModel(Long id) {
    	VisualModel vModel = VisualModel.find.where().eq("Id", id).findUnique();

    	if ( vModel != null) {
    		JsonNode json = Json.parse(vModel.jsonModel);		
    		return ok (
	    		json
		);
    	} else {
    		return notFound();
    	}
    }

    public static Result apk(Long id) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
        VisualModel vModel = VisualModel.find.where().eq("Id", id).findUnique();

        if ( vModel != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(vModel.jsonModel);
                ArrayNode nodes = (ArrayNode)root.path("nodes");
                ArrayNode links = (ArrayNode)root.path("links");

                List<Person> Entities = new ArrayList<Person>();
                List<Relationship> relationships = new ArrayList<Relationship>();

                for (int i = 0; i < nodes.size(); i++) {
                    long entityId = nodes.get(i).get("id").asLong();
                    String name = nodes.get(i).get("attributes").get("name").asText();

                    Person e = new Person(entityId, name);
                    Entities.add(e);
                }

                for (int j = 0; j < links.size(); j++) {
                    long linkId = links.get(j).get("id").asLong();
                    Person e1 = Entities.get(links.get(j).get("sourceID").asInt()-1);
                    Person e2 = Entities.get(links.get(j).get("targetID").asInt()-1);
                    String relationshipType = links.get(j).get("attributes").get("name").asText();

                    Relationship r = new Relationship(linkId, e1, e2, relationshipType);
                    relationships.add(r);
                }

                return ok(
                        new File(javaParse.run(relationships))
                );
            } catch (Exception e) {
                e.printStackTrace();
                return badRequest(vModel.jsonModel);
            }
        } else {
            return notFound();
        }
    }

    public static Result dsl(Long id) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	return ok();
    }
}
