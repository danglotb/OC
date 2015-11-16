set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set xtic rotate by -45
plot '../data/besteddinserthardScore.dat' using 2:xtic(1) title 'Score On Hard Prbl'