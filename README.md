> -
# Scala Crawler
*Crawling information from the web.*
>
> -

## What is this?
[Web Crawler](http://en.wikipedia.org/wiki/Web_crawler)

**This crawler is an implementation of the concept Web Crawler in Scala language.**

`Scala Crawler` is built to extract data from web pages based on a predefined set of rules defining fixed positions of HTML markups on the pages where the data we want is located.

Following are some of the highlights of this project:
- Scala language
- Eclipse project
- Gradle build management
- Proxy awareness
- config/seeds.txt defines a set of initial web pages to start off
- config/parsing.yml defines elements to be extracted using DOM structure
- config/config.properties defines the proxy values, database connections
- crawler.sql is the database schema where the extracted data is stored

A list of available commands:
- `gradle eclipse`: generate Eclipse project's settings 
- `gradle clean`: clean the build directory
- `gradle test`: run unit testing for Scala
