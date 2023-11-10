package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;

import dao.BlogDao;
import dao.BlogReaderDao;
import dao.ReaderxDao;
import entity.Blog;
import entity.BlogReader;
import entity.Reader;

@ManagedBean(name = "blogReaderBean")
public class BlogReaderBean {

	@SuppressWarnings({ "unused", "rawtypes" })
	private List blogreaders;
	private List<SelectItem> readers;
	private List<SelectItem> blogs;

	private String editId;
	private int readerId;
	private int blogId;

	private BlogReaderDao blogReaderDao;
	private BlogDao blogDao;
	private ReaderxDao readerDao;

	@PostConstruct
	private void init() {
		blogReaderDao = new BlogReaderDao();
		blogDao = new BlogDao();
		readerDao = new ReaderxDao();

		getReaders();
		getBlogs();
	}

	public String addNew() {
		try {

			var blogReader = new BlogReader();
			blogReader.setBlog(blogDao.getBlogByID(blogId));
			blogReader.setReader(readerDao.getReaderByID(readerId));
			blogReaderDao.addNew(blogReader);
			return "blogsreaders.xhtml?faces-redirect=true";

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		}
		return "";
	}

	public String deleteByID(int id) {
		try {
			blogReaderDao.delete(id);
			return "blogsreaders.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		}
		return "";
	}
	
	public String editByID() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("selectedId");
		var blog = blogReaderDao.getBlogReaderByID(Integer.valueOf(id));
		this.setEditId(id);
		this.setReaderId(blog.getReader().getId());
		this.setBlogId(blog.getBlog().getId());
		PrimeFaces.current().ajax().update(":blogEditForm");
		return "";
	}

	public String saveEdit() {
		try {
			String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("edId");
			int id = Integer.valueOf(ids);
			var blogReader = new BlogReader();
			var blog = blogDao.getBlogByID(blogId);
			var reader = readerDao.getReaderByID(readerId);
			blogReader.setId(id);
			blogReader.setBlog(blog);
			blogReader.setReader(reader);

			blogReaderDao.updateBlogReader(blogReader);
			return "blogsreaders.xhtml?faces-redirect=true";
			
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		}
		return "";
	}

	public List getBlogreaders() {
		return blogReaderDao.list();
	}

	public void setBlogreaders(List blogreaders) {
		this.blogreaders = blogreaders;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public List<SelectItem> getReaders() {
		var rdb = readerDao.list();
		List<SelectItem> xd = new ArrayList<>();
		if (rdb != null) {
			for (Object r : rdb) {
				if (r instanceof Reader) {
					var reader = (Reader) r;
					xd.add(new SelectItem(reader.getId(), reader.getName()));
				}
			}
		}
		return xd;
	}

	public void setReaders(List<SelectItem> readers) {
		this.readers = readers;
	}

	public List<SelectItem> getBlogs() {
		var rdb = blogDao.list();
		List<SelectItem> blogs = new ArrayList<>();
		if (rdb != null) {
			for (Object r : rdb) {
				if (r instanceof Blog) {
					var blog = (Blog) r;
					blogs.add(new SelectItem(blog.getId(), blog.getTitle()));
				}
			}
		}
		return blogs;
	}

	public void setBlogs(List<SelectItem> blogs) {
		this.blogs = blogs;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

}
