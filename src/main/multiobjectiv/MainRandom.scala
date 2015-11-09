package main.multiobjectiv

import data.multiobjectiv._
import solver.multiobjectiv._


object MainRandom extends App{

  def build500Random(solutions : List[(Int,Int)], instance : InstanceTSP) : List[(Int,Int)] = {
    if (solutions.length == 5)
      solutions
    else {
      val newSolution =  RandomSolver.solve(instance).score
      build500Random(solutions :+ (newSolution(0), newSolution(1)), instance)
    }
  }

  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")
  
  val solutions = build500Random(Nil, instance)
  
  println(solutions)
  
  println(Filter2o.filter(solutions))
    
}