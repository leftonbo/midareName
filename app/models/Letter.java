package models;

import java.util.*;

import javax.persistence.*;

import mt.Sfmt;
import play.Logger;
import play.db.ebean.*;
import play.data.validation.Constraints;

@Entity
public class Letter extends Model {

	/**
	 * SUID かな？
	 */
	private static final long serialVersionUID = -3229399137347456774L;

	@Id
	public long id;			// ID

    @Constraints.Required(message="Letters are required.")
	public String letter;	// Letter

    @Constraints.Required
    @Constraints.Min(0)
    public float startFrequency = 10.0f;

    @Constraints.Required
    @Constraints.Min(0)
    public float endOccurMult = 1.0f;
    
    // ==========

	/**
	 * The Finder
	 */
    public static final Finder<Long,Letter> find =
    	new Finder<Long,Letter>(Long.class, Letter.class);

	public static final List<Letter> all() {
		return find.all();
	}
	
	/**
	 * 名前の先頭に来る文字を取得
	 * 
	 * @return 重みランダムで得られた文字
	 */
	public static final Letter getStartLetter() {
		List<Letter> letters = find.where().gt("startFrequency", 0).findList();
		Sfmt rnd = new Sfmt();
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

	/**
	 * 次は何が来るかな？
	 * @param current 現在の文字
	 * @return 次の文字
	 */
	public static final Letter getNextLetter(Letter current) {
		// TODO LetterCarryを使う
		// 現在はランダム文字のみ
		List<Letter> letters = find.all();
		Sfmt rnd = new Sfmt();
		int upper = letters.size();
		
		return letters.get(rnd.NextInt(upper));
	}
}
