package graduation.alcoholic.web.board.visit.dto;

import graduation.alcoholic.domain.entity.Visit;
import lombok.Data;

@Data
public class VisitDto {
    private Long a_id;
    private Long female;
    private Long male;
    private Long twentys;
    private Long thirtys;
    private Long fourtys;
    private Long fiftys;


    public VisitDto(Visit v) {
        this.a_id=v.getA_id();
        this.female=v.getFemale();
        this.male=v.getMale();
        this.twentys=v.getTwentys();
        this.thirtys=v.getThirtys();
        this.fourtys=v.getFourtys();
        this.fiftys=v.getFiftys();
    }
}
