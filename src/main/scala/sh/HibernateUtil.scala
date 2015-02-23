package sh

import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.SessionFactory
import java.util.Properties
import java.io.FileInputStream

object HibernateUtil {
  private val sessionFactory = buildSessionFactory

  private def buildSessionFactory: SessionFactory = {
    try {
      val dbConnectionProperties:Properties= new Properties()
      dbConnectionProperties.load(new FileInputStream(Constants.FILE_CONFIG))
      
      // Create the SessionFactory from hibernate.cfg.xml
      return new AnnotationConfiguration().mergeProperties(dbConnectionProperties).configure().buildSessionFactory()

    } catch {
      case ex: Throwable => {
        // Make sure you log the exception, as it might be swallowed
        System.err.println("Initial SessionFactory creation failed." + ex);
        throw new ExceptionInInitializerError(ex);
      }
    }
  }

  def getSessionFactory = sessionFactory


  def shutdown {
    // Close caches and connection pools
    getSessionFactory.close();
  }
}