package com.mytweets

import spray.json.DefaultJsonProtocol

case class Tweet(tag:String, count: String)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val TweetFormat = jsonFormat2(Tweet)
}

