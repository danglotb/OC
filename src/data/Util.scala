package data

import sys.process._

/**
 * @author danglot
 */
object Util {

  def launchPlot(outPathname: String): Unit = {
    try {
      Seq("gnuplot", "-persist", outPathname + ".plt").!!
    } catch {
      case ioe: java.io.IOException => println("Required gnuplot set up on the machine. Check if it is in your Path")
    }
  }

  def write(name: String, str: String): Unit = {
    Some(new java.io.PrintWriter(name)).foreach { p => p.write(str); p.close }
  }
  
  def readOptimas() : scala.collection.mutable.ListBuffer[Int] = {
    val source = scala.io.Source.fromFile("input/wtbest100b.txt")
    val iterator = source getLines
    val optimas = scala.collection.mutable.ListBuffer[Int]()
    while (iterator hasNext) {
      try {
        optimas += ((iterator next).filter { x => x != ' ' }).toInt
      } catch {
        case (e: java.lang.NumberFormatException) =>
      }
    }
    optimas
  }
}