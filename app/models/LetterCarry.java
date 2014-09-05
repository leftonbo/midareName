package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.Constraints;

@Entity
public class LetterCarry extends Model {

	/**
	 * SUID かな？
	 */
	private static final long serialVersionUID = 3443882811971341882L;

	@Id
	public Long id;			// ID

    @Constraints.Required(message="Letters are required.")
    @ManyToOne
	public Letter letter;	// Letter

    @Constraints.Required(message="Next letters are required.")
    @ManyToOne
	public Letter next;		// Next Letter

    @Constraints.Required
    @Constraints.Min(0)
    public float frequency = 10.0f;
    
    // ==========

	/**
	 * The Finder
	 */
    public static final Finder<Long,LetterCarry> find =
    	new Finder<Long,LetterCarry>(Long.class, LetterCarry.class);

	public static final List<LetterCarry> all() {
		return find.all();
	}
	
}
