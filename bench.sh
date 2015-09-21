#!/bin/bash
#scala11 -cp bin main.MainHillClimbing -select first -neighbor exchange -init rnd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select first -neighbor exchange -init mdd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select first -neighbor exchange -init edd -k 30 -file input/wt100.txt
scala11 -cp bin main.MainHillClimbing -select first -neighbor swap -init rnd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select first -neighbor swap -init mdd -k 30 -file input/wt100.txt
scala11 -cp bin main.MainHillClimbing -select first -neighbor swap -init edd -k 30 -file input/wt100.txt
scala11 -cp bin main.MainHillClimbing -select first -neighbor insert -init rnd -k 30 -file input/wt100.txt
scala11 -cp bin main.MainHillClimbing -select first -neighbor insert -init mdd -k 30 -file input/wt100.txt
scala11 -cp bin main.MainHillClimbing -select first -neighbor insert -init edd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor exchange -init rnd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor exchange -init mdd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor exchange -init edd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor swap -init rnd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor swap -init mdd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor swap -init edd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor insert -init rnd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor insert -init mdd -k 30 -file input/wt100.txt
#scala11 -cp bin main.MainHillClimbing -select best -neighbor insert -init edd -k 30 -file input/wt100.txt