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
	
	public static final Letter getStartLetter() {
		List<Letter> letters = find.all();
		Sfmt rnd = new Sfmt();
		float sum;
		for (Letter l : letters) {
			
		}
		return null;
	}
	
}
