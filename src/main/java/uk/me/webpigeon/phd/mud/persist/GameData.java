package uk.me.webpigeon.phd.mud.persist;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class GameData implements Serializable {
	@PrimaryKey
	private String pk;
	
	public GameData(String pk) {
		this.pk = pk;
	}
	
	public String getPK() {
		return pk;
	}

}
