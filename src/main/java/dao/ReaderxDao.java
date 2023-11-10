package dao;

import java.util.List;

import entity.LocalEntityManagerFactory;
import entity.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class ReaderxDao {
	private EntityManager entityManager;

	public ReaderxDao() {
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
		Query queryObj = entityManager.createQuery("SELECT r FROM Reader r");
		List list = queryObj.getResultList();
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	public void createNewReader(String name) {
		entityManager.getTransaction().begin();
		Reader r = new Reader();
		r.setName(name);
		entityManager.persist(r);
		entityManager.getTransaction().commit();
	}

	public void deleteReader(int id) {
		entityManager.getTransaction().begin();
		Reader reader = new Reader();
		if (isReaderExists(id)) {
			reader.setId(id);
			entityManager.remove(entityManager.merge(reader));
		}
		entityManager.getTransaction().commit();
	}
	
	public void updateReader(int id, String name) {
		entityManager.getTransaction().begin();
		if(isReaderExists(id)) {
			Query queryObj = entityManager.createQuery("UPDATE Reader r SET r.name=:name WHERE r.id= :id");			
			queryObj.setParameter("id", id);
			queryObj.setParameter("name", name);
			int updateCount = queryObj.executeUpdate();
			if(updateCount > 0) {
				System.out.println("Record For Id: " + id + " Is Updated");
			}
		}
		entityManager.getTransaction().commit();
	}
	
	public Reader getReaderByID(int id) {
		Query queryObj = entityManager.createQuery("SELECT r FROM Reader r WHERE r.id = :id");
		queryObj.setParameter("id", id);
		Reader selectedId = (Reader) queryObj.getSingleResult();
		return selectedId;
	}

	private boolean isReaderExists(int id) {
		Reader selectedId = getReaderByID(id);
		return (selectedId != null);
	}

}
