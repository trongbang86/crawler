package sh

import edu.uci.ics.crawler4j.crawler.WebCrawler
import java.util.regex.Pattern
import edu.uci.ics.crawler4j.url.WebURL
import edu.uci.ics.crawler4j.crawler.Page
import edu.uci.ics.crawler4j.parser.HtmlParseData
import scala.collection.mutable.ListBuffer
import sh.services.LinkServices
import scala.collection.JavaConversions._
import sh.models.LinkStatus
import sh.models.Link
import sh.models.LinkContent
import java.io._
import org.jdom2.input.SAXBuilder
import org.jdom2.xpath.jaxen.JDOMXPath
import org.jdom2.Element
import org.jdom2.output.XMLOutputter
import org.apache.log4j.Logger

class MyCrawler extends WebCrawler {
  import MyCrawler._
  var domains: ListBuffer[String] = _

  /**
   * You should implement this function to specify whether
   * the given url should be crawled or not (based on your
   * crawling logic).
   */
  def checking(url: WebURL): Boolean = {
    val href = url.getURL().toLowerCase()
    if (domains == null) {
      domains = getMyController().getCustomData().asInstanceOf[ListBuffer[String]]
    }

    var flag = !FILTERS.matcher(href).matches() && domains.exists(n => href.startsWith(n))

    if (flag) { // if the url's extension is ok and in the domains we are checking
      val link = LinkServices.find(href)

      if (link != null) {
        link.touched += 1
        LinkServices.update(link)

        if (link.status == LinkStatus.PROCESSED.toString()) {
          return false // the url has been processed => no need to re-do it
        }

      }

    }

    return flag
  }

  /**
   * This function is called when a page is fetched and ready
   * to be processed by your program.
   */
  override def visit(page: Page) {
    val url = page.getWebURL().getURL().toLowerCase()
    logger.info(s"Visiting Page: $url")

    if (checking(page.getWebURL())) {
      logger.info(s"Processing page: $url")
      if (page.getParseData().isInstanceOf[HtmlParseData]) {
        var (link, linkContent) = LinkServices.findLinkAndContent(url)

        val updatedLinkAndContent = makeSureNotNull(link,linkContent,url)
        link= updatedLinkAndContent._1 
        linkContent= updatedLinkAndContent._2

        val htmlParseData = page.getParseData().asInstanceOf[HtmlParseData]

        val (xpath,titlePath) = UrlPatterns.getXPathAndTitle(url)
        
        logger.debug(s"XPath[$url]: $xpath")
    	logger.debug(s"Title Path[$url]: $titlePath")
    	
        if (xpath != "") {

          linkContent.content = extractContent(htmlParseData.getHtml(), xpath)
          linkContent.title = extractContent(htmlParseData.getHtml(), titlePath)
        		  
          
          LinkServices.saveOrUpdate(linkContent)
          LinkServices.saveOrUpdateProcessedLink(url)
        }
      }
    }
  }
  
  def makeSureNotNull($link: Link, $linkContent: LinkContent, $url:String): (Link,LinkContent) ={
    var (link,linkContent)= ($link,$linkContent)
    //if the url has never been stored, create a new set of link and link content in db
    if (link == null) {
      link = new Link($url)
      link.status = LinkStatus.STORED.toString()
      LinkServices.save(link)
    }

    if (linkContent == null) {
      linkContent = new LinkContent
      linkContent.link = link
    }
    return (link,linkContent)
  }

  def extractContent(html: String, xpath: String): String = {
    if (xpath == "") return "" //if no xpath value, don't save it

    val reader = new BufferedReader(new StringReader(html))

    val builder = new SAXBuilder("org.ccil.cowan.tagsoup.Parser")
    val doc = builder.build(reader)
    val domPath = new JDOMXPath(xpath)
    domPath.addNamespace("h", "http://www.w3.org/1999/xhtml");

    val content = domPath.selectSingleNode(doc).asInstanceOf[Element]
    return new XMLOutputter().outputString(content)

  }
}

object MyCrawler {
  val FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
    + "|png|tiff?|mid|mp2|mp3|mp4"
    + "|wav|avi|mov|mpeg|ram|m4v|pdf"
    + "|rm|smil|wmv|swf|wma|zip|rar|gz))$")
  val logger = Logger.getLogger(classOf[MyCrawler])
}