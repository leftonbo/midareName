package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result letterList() {
        return TODO;
    }
    public static Result letterNew() {
        return TODO;
    }
    public static Result letterWriteNew() {
        return TODO;
    }
    public static Result letterSelectList(long id) {
        return TODO;
    }
    
    public static Result letterCarryEdit(long id) {
        return TODO;
    }
    public static Result letterCarryWrite(long id) {
        return TODO;
    }
}
