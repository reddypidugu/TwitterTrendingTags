package com.mytweets
import java.io.{FileWriter, IOException}

import com.mytweets.MyJsonProtocol._
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import spray.json._

object TwitterTrendingTags  {

  def main(args: Array[String]) {
    val filters = args

    // adding Twitter security keys for Twitter API Access
    System.setProperty("twitter4j.oauth.consumerKey", "758I8BkuAvF7l5AUwNqjYYa2f")
    System.setProperty("twitter4j.oauth.consumerSecret", "HpC5puW1KMjI0xQiAtjgsNaXZ3s7Ok40QLK5yebpe5oB5vNx48")
    System.setProperty("twitter4j.oauth.accessToken", "47879036-qYq68GUR0mAlh458tMHdekeVtbb6vx2GC8RUMop8i")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "osHhBIDwzl4rJbnMeM7bR2shXBtoY2V5O5YCIDHUDIJeo")

    val spark = SparkSession
      .builder()
      .appName("Twitter Stream Popular Tags")
      .config("spark.sql.warehouse.dir", "file:///c:/tmp/spark-warehouse")
      .master("local[2]")
      .getOrCreate();

    val ssc = new StreamingContext(spark.sparkContext, Seconds(30))
    ssc.sparkContext.setLogLevel("ERROR")
    val stream = TwitterUtils.createStream(ssc, None, filters)

    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))

    val topCounts72Hrs = hashTags.map((_, 1))
                        .reduceByKeyAndWindow(_ + _, Seconds(259200)) // to get tags from last 72hrs
                        .map{case (topic, count) => (count, topic)}
                        .transform(_.sortByKey(false))


    topCounts72Hrs.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nTrending topics in last 72Hrs (%s total):".format(rdd.count()))

      topList.foreach{
        case (count, tag) =>
          println("tag:%s count:(%s tweets)".format(tag, count))

          //convert string to json string
          val jsonTag = Tweet(tag.toString, count.toString)
          val jsonTagString = jsonTag.toJson.prettyPrint
          println("Pretty String :%s ".format(jsonTagString))

          val file = new FileWriter("hashtags.json", true)
            try { //File Writer creates a file in write mode at the given location
              file.write(jsonTagString)
              file.flush()
            } catch {
              case e: IOException =>
                e.printStackTrace()
            } finally if (file != null) file.close()
          }
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
