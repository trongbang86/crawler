package sh.services

import sh.HibernateUtil
import sh.dao.GeneralDAO
import org.hibernate.Criteria
import org.hibernate.criterion.Restrictions
import sh.models.Link
import java.util.ArrayList
import scala.collection.JavaConversions._
import sh.models.LinkStatus
import sh.models.LinkContent

object LinkServices extends GeneralDAO {

  def saveOrUpdateProcessedLink(_url: String): Link = {
    var list: java.util.List[Link] = null
    var _link: Link = null
    var url = _url.toLowerCase()
    val session = Some(HibernateUtil.getSessionFactory.openSession())
    process(session, () => {
      val criteria = session.get.createCriteria(classOf[Link]);
      criteria.add(Restrictions.eq("url", url));
      list = criteria.list().asInstanceOf[java.util.List[Link]]
      if (list.size() > 0) { //processing existing links
        if (list.size() > 1) {
          throw new RuntimeException(s"Link[$url] is repeated ${list.size()} in the database")
        }

        list.foreach(link => {
          link.status = LinkStatus.PROCESSED.toString()
          link.processed_time += 1
          session.get.update(link)
          _link = link
        })
      } else { //this is a new link
        val link = new Link(url)
        link.status = LinkStatus.PROCESSED.toString()
        link.processed_time += 1
        session.get.save(link)
        _link = link
      }
    }, true)
    _link
  }

  def exist(_url: String): Boolean = {
    var ret: Boolean = false
    var url = _url.toString()
    val session = Some(HibernateUtil.getSessionFactory.openSession())
    find(session, () => {
      val criteria = session.get.createCriteria(classOf[Link]);
      criteria.add(Restrictions.eq("url", url));
      val result = criteria.list();
      if (result.size() > 0) {
        ret = true
      }
    })

    return ret
  }

  def find(_url: String): Link = {
    var ret: Link = null
    var url = _url.toString()
    var list: java.util.List[Link] = null
    val session = Some(HibernateUtil.getSessionFactory.openSession())
    find(session, () => {
      val criteria = session.get.createCriteria(classOf[Link]);
      criteria.add(Restrictions.eq("url", url));
      list = criteria.list().asInstanceOf[java.util.List[Link]]

    })

    if (list != null && list.size() > 1) {
      throw new RuntimeException(s"Link[$url] is repeated ${list.size()} in the database")
    }

    if (list != null && list.size() == 1) {
      ret = list.get(0)
    }

    return ret
  }

  def findLinkAndContent(_url: String): (Link, LinkContent) = {
    var link: Link = null
    var linkContent: LinkContent= null
    var url = _url.toString()
    var list: java.util.List[Link] = null
    val session = Some(HibernateUtil.getSessionFactory.openSession())
    find(session, () => {
      val criteria = session.get.createCriteria(classOf[Link]);
      criteria.add(Restrictions.eq("url", url));
      list = criteria.list().asInstanceOf[java.util.List[Link]]

      if (list != null && list.size() > 1) {
        throw new RuntimeException(s"Link[$url] is repeated ${list.size()} in the database")
      }

      if (list != null && list.size()==1) {
        link = list.get(0)
      }
      
      if(link!=null){
        linkContent= link.linkContent 
      }
      
    })

    return (link, linkContent)
  }

}