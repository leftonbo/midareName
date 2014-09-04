package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.db.ebean.Model.Finder;
import play.data.validation.Constraints;

@Entity
public class Letter extends Model {

	/**
	 * SUID かな？
	 */
	private static final long serialVersionUID = -3229399137347456774L;

	@Id
	public long id;			// ID

    @Constraints.Required
	public String letter;	// Letter

    @Constraints.Required
	public String next;		// Next Letter

    @Constraints.Required
    @Constraints.Min(1)
	public int numLimit;
    
    /**
     * Define default values
     */
    public Letter() {
    	numLimit = 2;
    }
    
    // ==========

	/**
	 * The Finder
	 */
    public static final Finder<Long,Letter> find =
    	new Finder<Long,Letter>(Long.class, Letter.class);

	public static final List<Letter> all() {
		return find.all();
	}
	
}
