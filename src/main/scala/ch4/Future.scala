package org.learningconcurrency
package ch4
/**
 * Created by curi on 2015/10/26.
 */

import scala.concurrent._
import ExecutionContext.Implicits.global

//object FuturesCreate extends App{
//  Future {log("the future is here")}
//  log("the future is coming")
//  Thread.sleep(1000)
//}

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