package entity;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@WebListener
public class LocalEntityManagerFactory implements ServletContextListener {

	private static EntityManagerFactory emf;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		emf = Persistence.createEntityManagerFactory("blog");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		emf.close();
	}

	public static EntityManager getEntityManager() {
		if (emf == null) {
			throw new IllegalStateException("Context is not initialized yet.");
		}
		return emf.createEntityManager();
	}

}
