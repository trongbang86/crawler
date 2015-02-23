package sh

object Main {
  
  def main(args: Array[String]) {
    val instructions = """|1. Generate config folder and file
    					|2. Crawl
    					|Choose a number:""".stripMargin
    print(instructions)
    val option = Console.readInt
    val options = Map(1 -> ConfigurationManager.generateConfig _, 2 -> CrawlRunner.crawlExecute _)

    options.get(option) match {
      case Some(f) => f()
      case None => println("Wrong option")
    }
    
    println("FINISHED")
    
  }
}