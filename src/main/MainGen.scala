package main

import data.Solution
import solver.GenSolver
import solver.MemSolver
import solver.HillClimbing
import data.InstanceReader

import scala.collection.mutable.ListBuffer

object MainMA extends App {
  
  val list = new scala.collection.mutable.ListBuffer[Solution]
  val N = 100
  val mRate = 0.40
  val cRate = 0.40
  val nbRun = 100
  
  data.Logger.open("output/GA"+N+"_"+nbRun+"_"+mRate+"_"+cRate+".log")

  val solutionIndex: ListBuffer[Int] = new ListBuffer[Int]()
  for (i <- 0 until 100) solutionIndex += i
  val reader = new InstanceReader(100, "input/wt100.txt", 125)
  val eddsol = new solver.EddSolver(100, new InstanceReader(100, "input/wt100.txt", 125))
  val mddsol = new solver.MddSolver(100, new InstanceReader(100, "input/wt100.txt", 125))
   
  for (i <- 0 until 125) {
    
    println("\t # " + i)
    
    val instance = reader.getInstance()
    
    for (i <- 0 until N - 2)
      list += new Solution(instance, scala.util.Random.shuffle(solutionIndex))

    eddsol.run
    list += eddsol.solution

    mddsol.run
    list += mddsol.solution

    val time = System.currentTimeMillis()
    val sol = MemSolver.run(nbRun, list, 0, cRate, mRate, 25, 75, HillClimbing.runForMA)
    data.Logger.write(i+"\t"+sol.score+"\t"+(System.currentTimeMillis() - time))    
  }
  
  data.Logger.close
}


/**
 * @author danglot
 */
object MainGen extends App {

  val list = new scala.collection.mutable.ListBuffer[Solution]
  val N = 100
  val nbMutation = 40
  val nbRun = 100
  
  data.Logger.open("output/GA"+N+"_"+nbRun+"_"+nbMutation+"_"+".log")

  val solutionIndex: ListBuffer[Int] = new ListBuffer[Int]()
  for (i <- 0 until 100) solutionIndex += i
  val reader = new InstanceReader(100, "input/wt100.txt", 125)
  val eddsol = new solver.EddSolver(100, new InstanceReader(100, "input/wt100.txt", 125))
  val mddsol = new solver.MddSolver(100, new InstanceReader(100, "input/wt100.txt", 125))
  
  for (i <- 0 until 125) {
    
    println("\t # " + i)
    
    val instance = reader.getInstance()
    
    for (i <- 0 until N - 2)
      list += new Solution(instance, scala.util.Random.shuffle(solutionIndex))

    eddsol.run
    list += eddsol.solution

    mddsol.run
    list += mddsol.solution

    val time = System.currentTimeMillis()
    val sol = GenSolver.run(nbMutation, nbRun, list, 0)
    data.Logger.write(i+"\t"+sol.score+"\t"+(System.currentTimeMillis() - time))    
  }
  
  data.Logger.close

}