package sh.services;

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import junit.framework.Assert._
import sh.models.Link
import sh.models.LinkStatus
import sh.Constants

@RunWith(classOf[JUnitRunner])
class LinkServicesTest extends FunSuite with BeforeAndAfter{
  
  before{
    Constants.HIBERNATE_CONFIG = Constants.HIBERNATE_CONFIG_TEST 
  }
  
  test ("LinkServices save method") {
	  val url= "http://test1.com"
	  val link = new Link(url)
	  link.status = LinkStatus.STORED.toString()
	  LinkServices.save(link)
  }
  
  test ("LinkServices find() throws exception for duplicates"){
	  val url= "http://test2.com"
	  val thrown = intercept[RuntimeException] {
		  val link1 = new Link(url)
		  LinkServices.save(link1)
  
  
		  val link2 = new Link(url)
		  LinkServices.save(link2)
		
		  LinkServices.find(url)
	  }
	  assertNotNull(thrown)
	  
	  val expected= s"Link[$url] is repeated 2 in the database"
	  
	  assertEquals(expected,thrown.getMessage)
	  
  }
  
}