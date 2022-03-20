package graduation.alcoholic.recommendation;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendScore{

    private Long id;

    private int score1;
    private int score2;
    private int score3;
    private int score4;
    private int score5;

    private int total_score;
    private double std;

    @Builder
    public RecommendScore(Long id, int score1, int score2, int score3, int score4, int score5, int total_score, int std) {
        this.id = id;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.score4 = score4;
        this.score5 = score5;
        this.total_score = total_score;
        this.std = getStd();
    }

    public double getStd() {

        double avg = (this.score1 + this.score2 + this.score3 + this.score4 + this.score5) / (double)5;
        double std = Math.sqrt(Math.pow(this.score1-avg, 2) + Math.pow(this.score2-avg, 2) + Math.pow(this.score3-avg, 2) + Math.pow(this.score4-avg, 2) + Math.pow(this.score5-avg, 2));

        return std;
    }

}
