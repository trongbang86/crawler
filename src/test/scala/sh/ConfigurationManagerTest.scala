package sh

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import junit.framework.Assert._

@RunWith(classOf[JUnitRunner])
class ConfigurationManagerTest extends FunSuite with BeforeAndAfter{

  before{
    Constants.CONFIG_FOLDER = "build/resources/test/config"
  }
  
  test ("ConfigurationManager generateConfig") {
    ConfigurationManager.generateConfig
    assertEquals(s"File [${Constants.FILE_CONFIG}] must exist",true, new java.io.File(Constants.FILE_CONFIG).exists)
    assertEquals(s"File [${Constants.FILE_SEEDS}] must exist",true, new java.io.File(Constants.FILE_SEEDS).exists)
    assertEquals(s"File [${Constants.FILE_PARSING}] must exist",true, new java.io.File(Constants.FILE_PARSING).exists)
  }
  
}