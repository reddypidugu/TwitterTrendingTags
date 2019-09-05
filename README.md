# TwitterTrendingTags
TwitterTrendingTags: Spark Streaming App to consume Twitter Trending Tags

## HOW TO START

* git clone https://github.com/reddypidugu/TwitterTrendingTags.git
* edit twitter4j.properties or modify main class with proper twitter credentials
* Run the below coammdn command to buildl the appliction jar and docker image.
```
mvn clean install
```
* The above command will create a docker image at /target as "myimage/TwitterTrendingTags:latest"
* Docker image contains the following command which will ensure Spark local mode cluster will spin up and application start consuming the tweets tagged with "Amsterdam" 
  ```
  spark-submit --master --packages org.apache.spark:spark-streaming-twitter_2.10:1.5.1 --jars spar
  k-core_2.11-1.5.2.logging.jar ./target/twittertrendingtags.jar Amsterdam
  ```
* To spin up the Docker image run the following command
```
 docker run myimage/TwitterTrendingTags:latest
``` 
  
* The hashtags retrieved from the Twitter API stream will be printed on console and also appended to "hashtags.json" file application directory where it is running.  

  

