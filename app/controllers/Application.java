package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	
	public static final String title = "乱れネーミング";
	public static final String version = "0.01";

    public static Result index() {
        return ok(index.render());
    }
    public static Result calc() {
        return TODO;
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

    public static Result letterCarryNew() {
        return TODO;
    }
    public static Result letterCarryEdit(long id) {
        return TODO;
    }
    public static Result letterCarryWrite(long id) {
        return TODO;
    }
}
