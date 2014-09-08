package controllers;

import static play.data.Form.form;

import java.util.*;

import models.Letter;
import models.LetterCarry;
import mt.Sfmt;
import play.data.Form;
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
        Form<LetterCarry> lForm = form(LetterCarry.class).fill(
        		new LetterCarry()
        );
        List<LetterCarry> lcl = LetterCarry.all();
        return ok(
            letcarryNewForm.render(lForm, lcl)
        );
    }
    public static Result letterCarryEdit(long id) {
        Form<LetterCarry> lForm = form(LetterCarry.class).fill(
        	LetterCarry.find.byId(id)
        );
        List<LetterCarry> lcl = LetterCarry.all();
        return ok(
            letcarryNewForm.render(lForm, lcl)
        );
    }
    public static Result letterCarryWriteNew() {
    	// 登録するよ
        Form<LetterCarry> lForm = form(LetterCarry.class).bindFromRequest();

        if(lForm.hasErrors()) {
        	// 変な値あったよ
        	if (lForm.hasGlobalErrors()) {
    	        flash("danger", lForm.globalError().message());
        	} else {
    	        flash("danger", "Some entries has invalid value.");
        	}
	        List<LetterCarry> lcl = LetterCarry.all();
            return badRequest(letcarryNewForm.render(lForm, lcl));
        }
        
        // 多重登録チェック
        boolean updateflag = false;
        Long updateID = 0L;
    	List<LetterCarry> lnx = 
				LetterCarry.find.where().eq("letter", lForm.get().letter).
				eq("next", lForm.get().next).findList();
    	if (lnx.size() >= 1) {
    		// 上書きフラグ発生
	        updateflag = true;
	        updateID = lnx.get(0).id;
    	}
    	
    	if (updateflag) {
        	// 上書き！
    		lForm.get().update(updateID);
	        flash("success", "Existed role has been changed.");
	        //flash("success", "Existed role '" + lForm.get().letter.toString() + " -> " +
	        //		lForm.get().next.toString() + "' has been changed.");
    	} else {
	        // 登録！
	        lForm.get().save();
	        flash("success", "Role has been created.");
	        //flash("success", "Role '" + lForm.get().letter.toString() + " -> " +
	        //		lForm.get().next.toString() + "' has been created.");
    	}
        
        // 次々と登録できるようにしておく
        List<LetterCarry> lcl = LetterCarry.all();
        return ok(
            letcarryNewForm.render(lForm, lcl)
        );
    }
    public static Result letterCarryDelete(long id) {
        Form<LetterCarry> lForm = form(LetterCarry.class).fill(
        	LetterCarry.find.byId(id)
        );
        LetterCarry.find.ref(id).delete();
        flash("success", "Role has been deleted.");
        List<LetterCarry> lcl = LetterCarry.all();
        return ok(
            letcarryNewForm.render(lForm, lcl)
        );
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
