package org.learningconcurrency
package ch4
/**
 * Created by curi on 2015/10/26.
 */

import scala.concurrent._
import ExecutionContext.Implicits.global

object FuturesCreate extends App{
  Future {log("the future is here")}
  log("the future is coming")
  Thread.sleep(1000)
}

import scala.io.Source
object FuturesDataType extends App{
  val buildFile: Future[String] = Future {
    val f = Source.fromFile("build.sbt")
    try f.getLines().mkString("\n") finally f.close()
  }
  log(s"started reading the build file asynchronously")
  log(s"status: ${buildFile.isCompleted}")
  Thread.sleep(250)
  log(s"status: ${buildFile.isCompleted}")
  log(s"value: ${buildFile.value}")
}

object FurturesCallbacks extends App {
  def getUrlSpec(): Future[List[String]] = Future {
    val url = "http://www.w3.org/Addressing/URL/url-spec.txt"
    val f = Source.fromURL(url)
    try f.getLines().toList finally f.close()
  }
  val urlSpec: Future[List[String]] = getUrlSpec()

  def find(lines: List[String], keyword: String): String =
    lines.zipWithIndex collect{
      case (line, n) if line.contains(keyword) => (n, line)
    } mkString("\n")

  urlSpec foreach{
    case lines => log(find(lines, "telnet"))
  }
  log("callback registered, continuing with other work")
  Thread.sleep(2000)
}

object FuturesFailure extends App {
  val urlSpec: Future[String] = Future{
    val invalidUrl = "http://www.w3.org/non-existent-url-spec.txt"
    Source.fromURL(invalidUrl).mkString
  }
  urlSpec.failed foreach{
    case t => log(s"exception occurred - $t")
  }
  log("callback registered, continuing with other work")
  Thread.sleep(1000)      // if not, the main thread may end, and so does the future thread
}

import java.io._
import scala.collection.convert.decorateAsScala._
//import org.apache.commons.io.FileUtils._