package graduation.alcoholic.recommendation;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendScore implements Comparable<RecommendScore>{

    private Long id;

    private int score_1;
    private int score_2;
    private int score_3;
    private int score_4;
    private int score_5;

    private int total_score;
    private double std;

    @Builder
    public RecommendScore(Long id, int score_1, int score_2, int score_3, int score_4, int score_5, int total_score, int std) {
        this.id = id;
        this.score_1 = score_1;
        this.score_2 = score_2;
        this.score_3 = score_3;
        this.score_4 = score_4;
        this.score_5 = score_5;
        this.total_score = total_score;
        this.std = getStd();
    }

    public double getStd() {

        double avg = (this.score_1 + this.score_2 + this.score_3 + this.score_4 + this.score_5) / (double)5;
        double std = Math.sqrt(Math.pow(this.score_1-avg, 2) + Math.pow(this.score_2-avg, 2) + Math.pow(this.score_3-avg, 2) + Math.pow(this.score_4-avg, 2) + Math.pow(this.score_5-avg, 2));

        return std;
    }

    @Override
    public int compareTo(RecommendScore r) {

        if(((r.getTotal_score() - this.total_score) == 1) & (r.getStd() > this.getStd()))
            return 1;
        else
            return -1;
    }
}
