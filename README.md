# TwitterTrendingTags
TwitterTrendingTags: Spark Streaming in a standalone project

## QUICK START

* install Apache Spark (http://spark.apache.org/):wq
* install sbt (http://www.scala-sbt.org/)
* git clone https://github.com/reddypidugu/TwitterTrendingTags.git
* edit twitter4j.properties or modify main class with proper twitter credentials
* mvn clean install
* spark-submit --master --packages org.apache.spark:spark-streaming-twitter_2.10:1.5.1 --jars spar
  k-core_2.11-1.5.2.logging.jar ./target/twittertrendingtags.jar Amsterdam
  
  ## How to Run a Docker Image
  

