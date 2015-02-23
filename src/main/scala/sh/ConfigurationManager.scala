package sh

import java.io.File

object ConfigurationManager {

  def inputToFile(is: java.io.InputStream, f: java.io.File) {
    val in = scala.io.Source.fromInputStream(is)
    val out = new java.io.PrintWriter(f)
    try { in.getLines().foreach( line => out.print( line + "\n" )) }
    finally { out.close }
  }

  def generateConfig() {
    
    val config_folder=new File(Constants.CONFIG_FOLDER)
    
    if(config_folder.exists()){
      val file_exists: String => Boolean= file => new File(file).exists()
      
      if(List(Constants.FILE_CONFIG , Constants.FILE_PARSING , Constants.FILE_SEEDS ).exists(file_exists)){
        println("The config folder is not empty. Please remove all the files if you want to regenerate all.")
      }else{
        copyConfigFiles
      }
      
    }else{
      config_folder.mkdir()
      copyConfigFiles
    }
  }
  
  def copyConfigFiles(){
    
    val loader = classOf[ClassLoader].getResourceAsStream _
    inputToFile(loader(Constants.FILE_CONFIG_SCAFFOLD), new File(Constants.FILE_CONFIG))
    inputToFile(loader(Constants.FILE_PARSING_SCAFFOLD ), new File(Constants.FILE_PARSING ))
    inputToFile(loader(Constants.FILE_SEEDS_SCAFFOLD  ), new File(Constants.FILE_SEEDS  ))
  }

}