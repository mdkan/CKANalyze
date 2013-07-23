package eu.trentorise.opendata.ckanalyze.managers;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import eu.trentorise.opendata.ckanalyze.jpa.Catalog;
import eu.trentorise.opendata.ckanalyze.jpa.CatalogStringDistribution;
import eu.trentorise.opendata.ckanalyze.jpa.Datatype;
import eu.trentorise.opendata.ckanalyze.jpa.ResourceDatatypesCount;
import eu.trentorise.opendata.ckanalyze.jpa.Resource;
import eu.trentorise.opendata.ckanalyze.jpa.ResourceStringDistribution;
import eu.trentorise.opendata.ckanalyze.services.PluralNamingStrategy;

/**
 * Provide access to the database
 * 
 * @author Alberto Zanella <a.zanella@trentorise.eu> Last modified by azanella
 *         On 12/lug/2013
 */
public final class PersistencyManager {
	private static SessionFactory sf = null;
	private static ServiceRegistry sr = null;

	private PersistencyManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Obtain the instance of configured sessionFactory to perform Hibernate
	 * query on DB
	 * 
	 * @return instance of sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sf == null) {
			sf = configure().buildSessionFactory(sr);

		}
		return sf;
	}

	/**
	 * Configuration method, here you have to put your annotated class in order
	 * to be processed from Hibernate
	 * 
	 * @return configuration instance
	 */
	private static Configuration configure() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(CatalogStringDistribution.class);
		configuration.addAnnotatedClass(ResourceStringDistribution.class);
		configuration.addAnnotatedClass(Catalog.class);
		configuration.addAnnotatedClass(Resource.class);
		configuration.addAnnotatedClass(Datatype.class);
		configuration.addAnnotatedClass(ResourceDatatypesCount.class);
		PluralNamingStrategy strategy = new PluralNamingStrategy();
		configuration.setNamingStrategy(strategy);
		configuration.configure();
		sr = new ServiceRegistryBuilder().applySettings(
				configuration.getProperties()).buildServiceRegistry();
		return configuration;
	}

	public static void insert(Object o) {
		Session ss = PersistencyManager.getSessionFactory().openSession();
		ss.beginTransaction();
		ss.persist(o);
		ss.getTransaction().commit();
		ss.close();
	}

	public static void update(Object o) {
		Session ss = PersistencyManager.getSessionFactory().openSession();
		ss.beginTransaction();
		ss.update(o);
		ss.getTransaction().commit();
		ss.close();
	}

	public static void insertInOneCommit(List<Object> os) {
		Session ss = PersistencyManager.getSessionFactory().openSession();
		ss.beginTransaction();
		for (Object object : os) {
			ss.persist(object);
		}
		ss.getTransaction().commit();
		ss.close();
	}
	
	public static Datatype getDatatypeByName(String name) {
		String hql = "FROM Datatype WHERE name = :name";
		Session ss = PersistencyManager.getSessionFactory().openSession();
		Query query = ss.createQuery(hql);
		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<Datatype> results = (List<Datatype>) query.list();
		if (results.isEmpty()) {
			ss.close();
			return null;
		} else {
			Datatype retval = results.get(0);
			ss.close();
			return retval;
		}
	}
}
