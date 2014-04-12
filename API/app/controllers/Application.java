package controllers;

import play.*;
import play.mvc.*;
import models.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result newVisModel() {
    	VisualModel vModel = new VisualModel();
    	vModel.save();
 	return created(
 		vModel.id.toString()
	);
    }

    public static Result updateVisModel(Long id) {

    	return ok(
    		VisualModel.find.where().eq("Id", id).findUnique().id.toString()
    	);
    }

    public static Result jar(Long id) {
    	return ok();
    }

    public static Result dsl(Long id) {
    	return ok();
    }
}
