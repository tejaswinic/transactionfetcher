# transactionfetcher
Fetch transactions from the last 60 secs

# Topic
Provide a summary of statistics for transactions in the last 60 secs

# Implementation
Used a NavigableMap with timestamp as key and List<Transaction> as value. Get the submap with all transactions in the last 60 secs.
DoubleSummaryStatistics(https://docs.oracle.com/javase/8/docs/api/java/util/DoubleSummaryStatistics.html) was used to collect various stats.

# Build
gradle clean build

# Running
gradle bootRun

# Rest Endpoint
localhost:8080/statistics

