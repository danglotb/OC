data1 <- read.table("instance1_algo1_hyp.30")

mean(data1[, 1])
median(data1[, 1])
min(data1[, 1])
max(data1[, 1])


data2 <- read.table("instance1_algo2_hyp.30")

mean(data2[, 1])
median(data2[, 1])
min(data2[, 1])
max(data2[, 1])


boxplot(data1[, 1], data2[, 1], ylab = "hypervolume difference", names = c("algo 1", "algo 2"))


wilcox.test(data1[, 1], data2[, 1], paired = TRUE, conf.level = 0.99)
