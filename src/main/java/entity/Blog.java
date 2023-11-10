package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="blogs")
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;	
	
	@Column(unique=true)
	private String title;
	
	private String description;
	
	public Blog() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
}
