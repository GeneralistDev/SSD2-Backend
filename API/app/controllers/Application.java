package controllers;

import play.*;
import play.libs.*;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import models.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result newVisModel() {
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

    public static Result jar(Long id) {
    	return ok();
    }

    public static Result dsl(Long id) {
    	return ok();
    }
}
