package controllers;

import models.Letter;
import mt.Sfmt;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	public static final String title = "乱れネーミング";
	public static final String version = "0.01";

    public static Result index() {
        return ok(index.render());
    }
    public static Result calc() {
    	String[] results = new String[10];
		Sfmt rnd = new Sfmt();
		
		// 生成するぞ
    	for (int i=0; i < results.length; i++) {
    		int letnum = 0;
    		Letter last = null;
    		results[i] = "";
    		do {
    			if (letnum == 0) {
    				// 1文字目
    				last = Letter.getStartLetter(rnd);
    				results[i] += last.letter;
    			} else {
    				// 2文字目以降   todo
    				last = Letter.getNextLetter(rnd, last);
    				results[i] += last.letter;
    			}
    			letnum ++;
    		} while (// 一定確率で止まる
    				rnd.NextUnif() >= getRandomStop(letnum) * last.endOccurMult
    				);
    	}
        return ok(calcResult.render(results));
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
    public static Result letterCarryWriteNew() {
        return TODO;
    }
    public static Result letterCarryEdit(long id) {
        return TODO;
    }
    public static Result letterCarryWrite(long id) {
        return TODO;
    }
    
    
    // =======================================================
    
    /**
     * その文字数で止まる確率
     * @param num 現在の文字数
     * @return 止まる確率
     */
    public static final float getRandomStop(int num) {
    	if (num <= 1) return 0.0f;
    	return (float)(1 - Math.pow(0.8, num-1));
    }
    
}
