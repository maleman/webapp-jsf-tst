package dao;

import java.util.List;

import jakarta.persistence.Query;
import entity.Blog;
import entity.LocalEntityManagerFactory;
import jakarta.persistence.EntityManager;

public class BlogDao {

	private EntityManager entityManager;

	public BlogDao() {
		super();
		try {
			this.entityManager = LocalEntityManagerFactory.getEntityManager();
		} catch (RuntimeException ex) {
			ex.printStackTrace(System.err);
			throw ex;
		}
	}

	public void close() {
		entityManager.close();
	}

	@SuppressWarnings("rawtypes")
	public List list() {
		Query queryObj = entityManager.createQuery("SELECT b FROM Blog b");
		List list = queryObj.getResultList();
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	public void addNew(Blog blog) {
		entityManager.getTransaction().begin();
		entityManager.persist(blog);
		entityManager.getTransaction().commit();
	}

	public void deleteBlog(int id) {
		entityManager.getTransaction().begin();
		var blog = new Blog();
		if (isBlogExists(id)) {
			blog.setId(id);
			entityManager.remove(entityManager.merge(blog));
		}
		entityManager.getTransaction().commit();
	}

	public Blog getBlogByID(int id) {
		Query queryObj = entityManager.createQuery("SELECT b FROM Blog b WHERE b.id = :id");
		queryObj.setParameter("id", id);
		Blog selectedBlog= (Blog) queryObj.getSingleResult();
		return selectedBlog;
	}

	private boolean isBlogExists(int id) {
		var selectedBlog = getBlogByID(id);
		return (selectedBlog != null);
	}
	
	
	public void updateBlog(Blog blog) {
		entityManager.getTransaction().begin();
		if(isBlogExists(blog.getId())) {
			Query queryObj = entityManager.createQuery("UPDATE Blog b SET b.title=:title, b.description=:description WHERE b.id= :id");			
			queryObj.setParameter("id", blog.getId());
			queryObj.setParameter("title", blog.getTitle());
			queryObj.setParameter("description", blog.getDescription());
			int updateCount = queryObj.executeUpdate();
			if(updateCount > 0) {
				System.out.println("Record For Id: " + blog.getId() + " Is Updated");
			}
		}
		entityManager.getTransaction().commit();
	}
}
