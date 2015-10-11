set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set xtic rotate by -45
set yrange[0:16]
plot '../data/ilsdeviation.dat' using 2:xtic(1) title 'Deviation'