/**
 * Created by curi on 2015/10/26.
 */
package org
package object learningconcurrency {
  def log(msg: String): Unit =
  println(s"${Thread.currentThread().getName}: $msg")
}
