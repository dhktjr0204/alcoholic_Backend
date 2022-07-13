package graduation.alcoholic.web.board.visit.dto;

import graduation.alcoholic.domain.entity.Visit;
import lombok.Builder;
import lombok.Data;

@Data
public class VisitResponseDto {
    private Long a_id;
    private Long female;
    private Long male;
    private Long twentys;
    private Long thirtys;
    private Long fourtys;
    private Long fiftys;


    @Builder
    public VisitResponseDto(Visit v) {
        this.a_id=v.getA_id();
        this.female=v.getFemale();
        this.male=v.getMale();
        this.twentys=v.getTwentys();
        this.thirtys=v.getThirtys();
        this.fourtys=v.getFourtys();
        this.fiftys=v.getFiftys();
    }
}
