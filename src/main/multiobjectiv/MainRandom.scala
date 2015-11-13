package main.multiobjectiv

import data.multiobjectiv._
import solver.multiobjectiv._
import data.Logger

object MainRandom extends App {

  def build500RandomOnLine(solutions: List[(Int, Int)], dominate: List[(Int, Int)], instance: InstanceTSP, i : Int): (List[(Int, Int)], List[(Int, Int)]) = {
    if (i == 500)
      (solutions,dominate)
    else {
      val newSolution = RandomSolver.solve(instance).score
      val dominators = Filter2o.filter(solutions :+ (newSolution(0), newSolution(1)))
      val dominate = (solutions :+ (newSolution(0), newSolution(1))).diff(dominators).distinct
      build500RandomOnLine(dominators, dominate, instance, i+1)
    }
  }

  def build500Random(solutions: List[(Int, Int)], instance: InstanceTSP): List[(Int, Int)] = {
    if (solutions.length == 500)
      solutions
    else {
      val newSolution = RandomSolver.solve(instance).score
      build500Random(solutions :+ (newSolution(0), newSolution(1)), instance)
    }
  }

  val instance = tspBuilder.build2mTsp("input/tsp/randomA100.tsp", "input/tsp/randomB100.tsp")

  //  val solutions = build500Random(Nil, instance)

  val solutions = build500RandomOnLine(Nil, Nil,instance,0)

  //build data
  var str = ""
  //  Filter2o.filter(solutions).foreach { x =>
  //    str += x._1 + "\t" + x._2 + "\n"
  //  }

  solutions._1.foreach { x =>
    str += x._1 + "\t" + x._2 + "\n"
  }
  data.Util.write("data/domRandom500mTSP.dat", str)

  str = ""
  solutions._2.foreach { x =>
    str += x._1 + "\t" + x._2 + "\n"
  }
  data.Util.write("data/random500mTSP.dat", str)

  //build plt file
  str = "plot \"data/random500mTSP.dat\" title \'non-dominate \',  \"data/domRandom500mTSP.dat\"title \'dominate\'"
  data.Util.write("plot/random500mTSP.plt", str)
}