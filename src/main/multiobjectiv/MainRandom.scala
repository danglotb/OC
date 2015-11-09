package main.multiobjectiv

import data.multiobjectiv._
import solver.multiobjectiv._
import data.Logger

object MainRandom extends App {

  def build500Random(solutions: List[(Int, Int)], instance: InstanceTSP): List[(Int, Int)] = {
    if (solutions.length == 500)
      solutions
    else {
      val newSolution = RandomSolver.solve(instance).score
      build500Random(solutions :+ (newSolution(0), newSolution(1)), instance)
    }
  }

  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")

  val solutions = build500Random(Nil, instance)

  //build data
  Logger.open("data/random500mTSP.dat")
  Filter2o.filter(solutions).foreach { x =>
    Logger.write(x._1 + "\t" + x._2 + "\n")
  }
  Logger.close()

  //build plt file
  Logger.open("plot/random500mTSP.plt")
  Logger.write("plot \'../data/random500mTSP.dat\'")
  Logger.close()

}