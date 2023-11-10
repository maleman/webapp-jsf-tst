package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import dao.BlogDao;
import entity.Blog;

@SuppressWarnings("rawtypes")
@ManagedBean(name = "blogBean")
public class BlogBean {
	
	private List blogs;

	private String editId;
	private String title;
	private String description;

	private BlogDao blogdao;

	@PostConstruct
	private void init() {
		blogdao = new BlogDao();
	}

	public String addNew() {
		try {
			if ((title != null && title.length() > 0) && (description != null && description.length() > 0)) {
				var blog = new Blog();
				blog.setTitle(title);
				blog.setDescription(description);
				blogdao.addNew(blog);
				return "blogs.xhtml?faces-redirect=true";
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		}
		return "";
	}

	public String deleteByID(int id) {
		blogdao.deleteBlog(id);
		return "blogs.xhtml?faces-redirect=true";
	}

	public String editByID() {
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("selectedreaderId");
		var blog = blogdao.getBlogByID(Integer.valueOf(id));
		this.setEditId(id);
		this.setTitle(blog.getTitle());
		this.setDescription(blog.getDescription());
		PrimeFaces.current().ajax().update(":blogEditForm");
		return "";
	}

	public String saveEdit() {
		try {
			String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("editedId");
			int id = Integer.valueOf(ids);
			var blog = new Blog();
			blog.setId(id);
			blog.setTitle(title);
			blog.setDescription(description);
			blogdao.updateBlog(blog);
			return "blogs.xhtml?faces-redirect=true";
			
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
		}
		return "";
	}

	public List getBlogs() {
		blogs = blogdao.list();
		return blogs;
	}

	public void setBlogs(List blogs) {
		this.blogs = blogs;
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

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

}
