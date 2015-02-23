package sh

object Constants {
  
  val CONFIG_FOLDER= "config"
  
  val FILE_CONFIG_NAME= "config.properties"
  val FILE_CONFIG_SCAFFOLD = s"/scaffold/${FILE_CONFIG_NAME}"
  val FILE_CONFIG = s"${CONFIG_FOLDER}/${FILE_CONFIG_NAME}"
  
  val FILE_PARSING_NAME = "parsing.yml"
  val FILE_PARSING_SCAFFOLD = s"/scaffold/${FILE_PARSING_NAME}"
  val FILE_PARSING = s"${CONFIG_FOLDER}/${FILE_PARSING_NAME}"
    
  val FILE_SEEDS_NAME = "seeds.txt"  
  val FILE_SEEDS_SCAFFOLD = s"/scaffold/${FILE_SEEDS_NAME}"
  val FILE_SEEDS = s"${CONFIG_FOLDER}/${FILE_SEEDS_NAME}"
}