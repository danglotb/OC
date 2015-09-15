package main

import solver._

object MainHillClimbing extends App {
  
  HillClimbing.run(HillClimbing.initRandom(), 10, HillClimbing.insertGen, HillClimbing.selectFirst)
  
}


