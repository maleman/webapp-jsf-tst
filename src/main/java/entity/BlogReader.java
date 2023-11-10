package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="blogs_readers")
public class BlogReader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;	
	
	@ManyToOne
	@JoinColumn(name = "blogId")
	private Blog blog;
	
	@ManyToOne
	@JoinColumn(name = "readerId")
	private Reader reader;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}
	
	
}
