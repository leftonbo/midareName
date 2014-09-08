package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

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
    
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (letter.id == null) {
            errors.add(new ValidationError("letter", "Letters are required."));
        }
        if (next.id == null) {
            errors.add(new ValidationError("next", "Next Letters are required."));
        }
        return errors.isEmpty() ? null : errors;
    }
    
    // ==========

	/**
	 * The Finder
	 */
    public static final Finder<Long,LetterCarry> find =
    	new Finder<Long,LetterCarry>(Long.class, LetterCarry.class);

	public static final List<LetterCarry> all() {
		return find.orderBy("next").orderBy("letter").findList();
	}
	
}
