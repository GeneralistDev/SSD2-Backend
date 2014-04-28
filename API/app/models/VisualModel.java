package models;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class VisualModel extends Model {

	@Id
	public Long id;

	@Column(columnDefinition = "TEXT")
	public String jsonModel;

	public VisualModel() {}

	public static Finder<Long, VisualModel> find = new Model.Finder(
		Long.class, VisualModel.class
	);
}