package main

import solver._

object MainHillClimbing extends App {
  
  HillClimbing.run(HillClimbing.initRandom(), 10, HillClimbing.swapGen, HillClimbing.selectFirst)
  
}


