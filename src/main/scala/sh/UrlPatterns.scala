package sh

import org.yaml.snakeyaml.Yaml
import java.io._
import java.util._
import scala.collection.JavaConversions._
import java.util.regex._
import org.apache.log4j.Logger

class UrlPatterns{}

object UrlPatterns {
  val logger= Logger.getLogger(classOf[UrlPatterns])
  val URL = "url"
  val XPATH = "xpath"
  val PATTERN = "pattern"
  val TITLE= "title"

  private val yaml = new Yaml();
  private var patterns: List[Map[String, Object]] = null

  logger.info(s"Loading resouce: ${Constants.FILE_PARSING} ")
  val ios: InputStream = new FileInputStream(Constants.FILE_PARSING )

  // Parse the YAML file and return the output as a series of Maps and Lists
  patterns = yaml.load(ios).asInstanceOf[Map[String, Object]].get("patterns").asInstanceOf[List[Map[String, Object]]]

  //parsing all the given urls to RegEx before hand
  patterns.map(p => p.put( PATTERN , Pattern.compile(p.get("url").asInstanceOf[String])))
  
  patterns.map(p => logger.info(s"Pattern: ${p.get(URL)} - ${p.get(XPATH )}"))
  
  private def queryURL(url:String, f:Map[String,Object] => (String,String)):(String,String)={
    logger.info(s"queryURL: $url")
    for (pattern <- patterns) {
      logger.info(s"Using pattern[${pattern.get(URL)}] for $url")
      val matcher= pattern.get(PATTERN).asInstanceOf[Pattern].matcher(url)
      if (matcher.matches()) {
        return f(pattern)
      }
    }
    return ("","")
  } 

  def getXPath(url: String): String = {
    val f:Map[String,Object] => (String,String)= (pattern:Map[String,Object]) 
    											=> (pattern.get(UrlPatterns.XPATH).asInstanceOf[String], "")
    queryURL(url, f)._1 
  }
  
  def getTitlePath(url: String): String = {
    val f:Map[String,Object] => (String,String)= (pattern:Map[String,Object]) 
											=> (pattern.get(UrlPatterns.TITLE).asInstanceOf[String], "")
    queryURL(url, f)._1
  }
  
  
  def getXPathAndTitle(url: String): (String,String) = {
    val f:Map[String,Object] => (String,String)= (pattern:Map[String,Object]) 
										=> (pattern.get(UrlPatterns.XPATH).asInstanceOf[String],pattern.get(UrlPatterns.TITLE).asInstanceOf[String])
    queryURL(url, f)
  }
  
}