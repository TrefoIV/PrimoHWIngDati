package stats;

public class QueryStats {

    private long tot_lucene_query_time;
    private double mean_lucene_query_time;
    private long tot_query_time;

    public QueryStats(long tot_lucene_query_time, double mean_lucene_query_time, long tot_query_time) {
        this.tot_lucene_query_time = tot_lucene_query_time;
        this.mean_lucene_query_time = mean_lucene_query_time;
        this.tot_query_time = tot_query_time;
    }

    public long getTot_query_time() {
        return tot_query_time;
    }

    public double getMean_lucene_query_time() {
        return mean_lucene_query_time;
    }

    public long getTot_lucene_query_time() {
        return tot_lucene_query_time;
    }

    @Override
    public String toString() {
        return "\nQueryStats:" +
                "\ntot_lucene_query_time=" + tot_lucene_query_time +
                "\nmean_lucene_query_time=" + mean_lucene_query_time +
                "\ntot_query_time=" + tot_query_time +
                '\n';
    }
}
