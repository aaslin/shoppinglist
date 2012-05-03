package se.aaslin.developer.shoppinglist.android.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import se.aaslin.developer.shoppinglist.android.entity.meta.Property_;

@Entity(name = "property")
public class Property {
	
	@Id
	@Column(name = Property_.id)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(name = Property_.key, nullable = false, length = 256)
	private String key;

	@Column(name = Property_.value, nullable = false, length = 256)
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
