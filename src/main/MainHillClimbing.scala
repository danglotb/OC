package main

import solver._

object MainHillClimbing extends App {
  
  HillClimbing.run(HillClimbing.initMdd, 10, HillClimbing.swapGen, HillClimbing.selectFirst)
  
}


