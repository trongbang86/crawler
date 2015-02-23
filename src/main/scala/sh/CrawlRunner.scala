package sh

import java.util.Properties
import edu.uci.ics.crawler4j.crawler.CrawlConfig
import java.lang.Boolean
import java.io.FileInputStream
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer
import edu.uci.ics.crawler4j.crawler.CrawlController
import scala.collection.mutable.ListBuffer
import scala.io.Source

class MyProperties extends Properties {
  override def getProperty(key: String) = super.getProperty(key).trim()
}

object CrawlRunner {
  def createConfig(configProperties: Properties): CrawlConfig = {
    val config = new CrawlConfig()
    val crawlStorageFolder = configProperties.getProperty("crawlStorageFolder")
    config.setCrawlStorageFolder(crawlStorageFolder)
    config.setPolitenessDelay(1000);
    config.setMaxPagesToFetch(-1);
    if (configProperties.getProperty("proxyEnabled").toBoolean) {
      config.setProxyHost(configProperties.getProperty("proxyHost"));
      config.setProxyPort(Integer.parseInt(configProperties.getProperty("proxyPort")))
      config.setProxyUsername(configProperties.getProperty("proxyUsername"));
      config.setProxyPassword(configProperties.getProperty("proxyPassword"));
    }
    config.setResumableCrawling(Boolean.TRUE)
    return config
  }
  
  def crawlExecute() {
    val configProperties = new MyProperties
    configProperties.load(new FileInputStream(Constants.FILE_CONFIG))

    val numberOfCrawlers = 1
    val config = createConfig(configProperties)

    /*
		* Instantiate the controller for this crawl.
		*/
    val pageFetcher = new PageFetcher(config)
    val robotstxtConfig = new RobotstxtConfig()
    val robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher)
    val controller = new CrawlController(config, pageFetcher, robotstxtServer)

    /*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
    val domains = new ListBuffer[String]()

    for (line <- Source.fromInputStream(new FileInputStream(Constants.FILE_SEEDS)).getLines()) {
      domains += line
      controller.addSeed(line)
    }
    controller.setCustomData(domains)

    controller.startNonBlocking(classOf[MyCrawler], numberOfCrawlers)
    Thread.sleep(configProperties.getProperty("durationInMins").toInt * 60 * 1000)
    controller.Shutdown()
    controller.waitUntilFinish()

  }
}