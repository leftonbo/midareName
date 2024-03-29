package models;

import java.util.*;

import javax.persistence.*;

import mt.Sfmt;
import play.db.ebean.*;
import play.data.validation.Constraints;

@Entity
public class Letter extends Model {

	/**
	 * SUID かな？
	 */
	private static final long serialVersionUID = -3229399137347456774L;

	@Id
	public Long id;			// ID

    @Constraints.Required(message="Letters are required.")
	public String letter;	// Letter

    @Constraints.Required
    @Constraints.Min(0)
    public float startFrequency = 10.0f;

    @Constraints.Required
    @Constraints.Min(0)
    public float endOccurMult = 1.0f;
    
    public String toString() {
    	return letter;
    }
    
    // ==========

	/**
	 * The Finder
	 */
    public static final Finder<Long,Letter> find =
    	new Finder<Long,Letter>(Long.class, Letter.class);
    
    /**
     * for selector
     * @return
     */
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Letter l: Letter.find.orderBy("letter").findList()) {
            options.put(l.id.toString(), l.letter);
        }
        return options;
    }

	public static final List<Letter> all() {
		return find.all();
	}
	
	/**
	 * 名前の先頭に来る文字を取得
	 * @param rnd メルセンヌさん
	 * @return 重みランダムで得られた文字
	 */
	public static final Letter getStartLetter(Sfmt rnd) {
		List<Letter> letters = find.where().gt("startFrequency", 0).findList();
		float sum = 0f;

		for (Letter l : letters) {
			float f = l.startFrequency;
			l.startFrequency += sum;
			sum += f;
		}
		float weight = (float) (rnd.NextUnif() * sum);
		
		// 2分法じゃないか！
		int lower = -1;
		int upper = letters.size();
		while (upper - lower > 1) {
			int index = (lower + upper) / 2;
			if (weight < letters.get(index).startFrequency) {
				upper = index;
			} else {
				lower = index;
			}
		}
		
		return letters.get(upper);
	}
	public static final Letter getStartLetter() {
		return getStartLetter(new Sfmt());
	}

	/**
	 * 次は何が来るかな？
	 * @param rnd メルセンヌさん
	 * @param current 現在の文字
	 * @return 次の文字
	 */
	public static final Letter getNextLetter(Sfmt rnd, Letter current) {
		List<Letter> letters = find.all();
		float sum = 0f;

		Iterator<Letter> it = letters.iterator();
		while (it.hasNext()) {
			Letter l = it.next();
			// 面倒だから使い回し
			l.startFrequency = 0.1f;
			// Carry がある場合はそっちにする
			List<LetterCarry> lnx = 
					LetterCarry.find.where().eq("letter", current).
					eq("next", l).findList();
			if (lnx.size() >= 1) {
				l.startFrequency = lnx.get(0).frequency;
			}
			
			// freq = 0 はリストからさくじょ
			float f = l.startFrequency;
			if (f == 0) {
				it.remove();
			} else {
				l.startFrequency += sum;
				sum += f;
			}
		}
		float weight = (float) (rnd.NextUnif() * sum);
		
		// 2分法じゃないか！
		int lower = -1;
		int upper = letters.size();
		while (upper - lower > 1) {
			int index = (lower + upper) / 2;
			if (weight < letters.get(index).startFrequency) {
				upper = index;
			} else {
				lower = index;
			}
		}
		
		return letters.get(upper);
	}
	public static final Letter getNextLetter(Letter current) {
		return getNextLetter(new Sfmt(), current);
	}
}
