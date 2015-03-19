package sh

object Constants {
  
  var CONFIG_FOLDER= "config"
  var SCAFFOLD_FOLDER= "scaffold"
    
  def FILE_CONFIG_NAME= "config.properties"
  def FILE_CONFIG_SCAFFOLD = s"/${SCAFFOLD_FOLDER}/${FILE_CONFIG_NAME}"
  def FILE_CONFIG = s"${CONFIG_FOLDER}/${FILE_CONFIG_NAME}"
  
  def FILE_PARSING_NAME = "parsing.yml"
  def FILE_PARSING_SCAFFOLD = s"/${SCAFFOLD_FOLDER}/${FILE_PARSING_NAME}"
  def FILE_PARSING = s"${CONFIG_FOLDER}/${FILE_PARSING_NAME}"
    
  def FILE_SEEDS_NAME = "seeds.txt"  
  def FILE_SEEDS_SCAFFOLD = s"/${SCAFFOLD_FOLDER}/${FILE_SEEDS_NAME}"
  def FILE_SEEDS = s"${CONFIG_FOLDER}/${FILE_SEEDS_NAME}"
  
  var HIBERNATE_CONFIG= "hibernate.cfg.xml"
  var HIBERNATE_CONFIG_TEST= "hibernate_test.cfg.xml"
}