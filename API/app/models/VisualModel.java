package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;
import com.fasterxml.jackson.databind.JsonNode;

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