package main

import data._

/**
 * @author danglot
 */
object Benchmark extends App {
  
  var selectFunc: ((((Solution) => Solution), Solution) => (Solution)) = _
  var initFunc: (String) => Unit = _
  var genFunc: (Solution) => Solution = _
  var nbRuns: Int = 30
  var pathname: String = ""
  
  
}