package controllers;

import play.*;
import play.mvc.*;
import models.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result checkPreFlight(String all) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
	response().setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
	response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
	return ok();
}
    public static Result newVisModel() {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	VisualModel vModel = new VisualModel();
    	vModel.save();
 	return created(
 		vModel.id.toString()
	);
    }

    public static Result updateVisModel(Long id) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	return ok(
    		VisualModel.find.where().eq("Id", id).findUnique().id.toString()
    	);
    }

    public static Result jar(Long id) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	return ok();
    }

    public static Result dsl(Long id) {
    	response().setHeader("Access-Control-Allow-Origin", "*");
    	return ok();
    }
}
