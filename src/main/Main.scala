package main

import data._
import solver._

/**
 * @author danglot
 */

object Main extends App {
  val reader = new InstanceReader(100, "input/wt100.txt", 125)
  //  val solver : RandomSolver = new RandomSolver(100,reader)
  val solver: Solver = new MddSolver(100, reader)
  while (reader.hasNext())
    solver.run()
}
