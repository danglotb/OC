set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set xtic rotate by -45
plot '../data/1rstInstance.dat' using 5:xtic(1) title 'Score on Time(S/T)'