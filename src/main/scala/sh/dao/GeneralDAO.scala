package sh.dao

import sh.models.Link
import org.hibernate.Session
import sh.HibernateUtil
import org.hibernate.Transaction

trait GeneralDAO {
  def save(obj: AnyRef) {

    val session = Some(HibernateUtil.getSessionFactory.openSession())
    process(session, () => session.get.save(obj), true)

  }
  
  
  def saveOrUpdate(obj: AnyRef) {

    val session = Some(HibernateUtil.getSessionFactory.openSession())
    process(session, () => session.get.saveOrUpdate(obj), true)

  }

  def update(obj: AnyRef) {
    val session = Some(HibernateUtil.getSessionFactory.openSession())
    process(session, () => session.get.update(obj), true)
  }

  protected def process(session: Option[Session], callback: () => Unit, txEnabled: Boolean = false) {
    var transaction: Option[Transaction] = None
    try {
      if (txEnabled) {
        transaction = Some(session.get.getTransaction())
        transaction.get.begin()
      }

      callback()
      if (txEnabled) {
        transaction.get.commit()
      }

      HibernateUtil.shutdown
    } catch {
      case re: RuntimeException => {

        if (txEnabled) {
          try {
            transaction.get.rollback()
          } catch {
            case _: RuntimeException => {
              println("Couldn't roll back transaction")
            }
          }
        }
        throw re
      }
    } finally {
      if (session.isDefined) {
        session.get.close
      }
    }

  }

  def find(session: Option[Session], callback: () => Unit){
    process(session: Option[Session], callback: () => Unit, false)
  }

}