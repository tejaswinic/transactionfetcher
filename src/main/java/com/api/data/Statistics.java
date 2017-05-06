package com.api.data;

/**
 * Created by tchintalapudi on 04/05/17.
 */
public class Statistics {
    private final Double sum;
    private final Double avg;
    private final Double max;
    private final Double min;
    private final long count;

    public Statistics(Double sum, Double avg, Double max, Double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public Double getAvg() {
        return avg;
    }

    public Double getMax() {
        return max;
    }

    public Double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }
}
