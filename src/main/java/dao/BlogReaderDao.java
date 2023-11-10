package dao;

import java.util.List;

import entity.BlogReader;
import entity.LocalEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class BlogReaderDao {
	private EntityManager entityManager;

	public BlogReaderDao() {
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
		Query queryObj = entityManager.createQuery("SELECT br FROM BlogReader br");
		List list = queryObj.getResultList();
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}
	
	public void addNew(BlogReader blogReader) {
		entityManager.getTransaction().begin();
		entityManager.persist(blogReader);
		entityManager.getTransaction().commit();
	}
	
	
	public void delete(int id) {
		entityManager.getTransaction().begin();
		var blog = new BlogReader();
		if (exists(id)) {
			blog.setId(id);
			entityManager.remove(entityManager.merge(blog));
		}
		entityManager.getTransaction().commit();
	}
	
	public void updateBlogReader(BlogReader blogReader) {
		entityManager.getTransaction().begin();
		if(exists(blogReader.getId())) {
			Query queryObj = entityManager.createQuery("UPDATE BlogReader br SET br.blog=:blog, br.reader=:reader WHERE br.id= :id");			
			queryObj.setParameter("id", blogReader.getId());
			queryObj.setParameter("blog", blogReader.getBlog());
			queryObj.setParameter("reader", blogReader.getReader());
			int updateCount = queryObj.executeUpdate();
			if(updateCount > 0) {
				System.out.println("Record For Id: " + blogReader.getId() + " Is Updated");
			}
		}
		entityManager.getTransaction().commit();
	}
	public BlogReader getBlogReaderByID(int id) {
		Query queryObj = entityManager.createQuery("SELECT b FROM BlogReader b WHERE b.id = :id");
		queryObj.setParameter("id", id);
		BlogReader selectedBlogreader= (BlogReader) queryObj.getSingleResult();
		return selectedBlogreader;
	}
	
	private boolean exists(int id) {
		var selectedBlog = getBlogReaderByID(id);
		return (selectedBlog != null);
	}

}
