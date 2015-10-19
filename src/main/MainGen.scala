package main

import data.Solution
import solver.GenSolver
import solver.HillClimbing
import data.InstanceReader

import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */
object MainGen extends App {
  
  val list = new scala.collection.mutable.ListBuffer[Solution]
  
  val N = 6
  
  val solutionIndex : ListBuffer[Int] = new ListBuffer[Int]()
  for (i <- 0 until 100) solutionIndex += i
  val reader = new InstanceReader(100, "input/wt100.txt", 125)
  val instance = reader.getInstance()
 
  for (i <- 0 until N) 
     list += new Solution(instance, scala.util.Random.shuffle(solutionIndex))
  
  println(GenSolver.run(list, 0))  
  
}