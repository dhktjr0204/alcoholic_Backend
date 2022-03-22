package graduation.alcoholic.recommend;


import graduation.alcoholic.domain.Alcohol;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private RecommendTypeDto recommendTypeDto;
    private final RecommendRepository recommendRepository;
    static int i=0;
    private static List<Alcohol> list=new ArrayList<>();
    @Transactional
    public Map<String,RecommendTypeDto> returnType(String type){
        Map<String,RecommendTypeDto> res=new HashMap<>();
        RecommendTypeDto type_Taste=null;
        if(type=="청주"){
            type_Taste= recommendTypeDto.builder()
                    .taste_1("단맛")
                    .taste_2("산미")
                    .taste_3("씁쓸")
                    .taste_4("바디감")
                    .taste_5("담백").build();
        }

        res.put("청주",type_Taste);
        return res;
    }

    public void removeDup(List<Alcohol> resultList){
        for(int k=0;k<resultList.size();k++){
            if(!list.contains(resultList.get(k))){
                list.add(resultList.get(k));
            }
        }
    }

    @Transactional
    public List<Alcohol> recommendAlcohol(RecommendDto recommendDto){
        //루프 돌 때마다 필터링된 정보 저장함
        List<Alcohol> resultList;

        //필터링 결과 보여주는 list
        //첫번째니까 중복될 일이 없어서 그냥 저장
        list=recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3AndTaste4AndTaste5(
                recommendDto.getType(),recommendDto.getStart_degree(),recommendDto.getEnd_degree(),
                recommendDto.getStart_price(),recommendDto.getEnd_price(),
                recommendDto.getTaste_1(),recommendDto.getTaste_2(),recommendDto.getTaste_3(),recommendDto.getTaste_4(),recommendDto.getTaste_5());


        while(list.size()<4) {
            if (i == 0) {
                //일치하는 결과 저장
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3AndTaste4(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_3(), recommendDto.getTaste_4());

                //중복제거 후 보여줄 list에 저장
                removeDup(resultList);

                //다른 루프를 돌아야되니 초기화
                resultList.clear();
                System.out.println("맛5 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 1) {

                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_3(), recommendDto.getTaste_5());
                removeDup(resultList);
                resultList.clear();
                System.out.println("맛4 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 2) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste4AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_4(), recommendDto.getTaste_5());
                removeDup(resultList);

                resultList.clear();
                System.out.println("맛3 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 3) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste4AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_3(), recommendDto.getTaste_4(), recommendDto.getTaste_5());
                removeDup(resultList);
                resultList.clear();
                System.out.println("맛2 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 4) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste4AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_3(), recommendDto.getTaste_4(), recommendDto.getTaste_5());
                removeDup(resultList);
                resultList.clear();
                System.out.println("맛1 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 5) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_3());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛4/맛5 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 6) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste4(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_4());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛3/맛5 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());

            }

            if (i == 7) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste4(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_3(), recommendDto.getTaste_4());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛2/맛5 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 8) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3AndTaste4(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_2(), recommendDto.getTaste_3(), recommendDto.getTaste_4());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛1/맛5 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 9) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛3/맛4 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 10) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_3(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛2/맛4 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 11) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_2(), recommendDto.getTaste_3(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛1/맛4 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 12) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste4AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_4(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛2/맛3 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }

            if (i == 13) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste4AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_2(), recommendDto.getTaste_4(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛1/맛3 제거");
                System.out.println("리스트 저장된 갯수:" + list.size());
            }
//
//            if(i==8){
//                resultList=recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste3(
//                        recommendDto.getType(), recommendDto.getStart_degree()-5, recommendDto.getEnd_degree()+5,
//                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
//                        recommendDto.getTaste_1(), recommendDto.getTaste_2(), recommendDto.getTaste_3());
//                removeDup(resultList);
//                System.out.println(resultList.size());
//
//                resultList.clear();
//                System.out.println("바디/담백 제거&주량늘리기");
//                System.out.println(list.size());
//            }
//            if(i==9){
//                resultList=recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste5(
//                        recommendDto.getType(), recommendDto.getStart_degree()-5, recommendDto.getEnd_degree()+5,
//                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
//                        recommendDto.getTaste_1(), recommendDto.getTaste_2(),recommendDto.getTaste_5());
//                removeDup(resultList);
//                System.out.println(resultList.size());
//
//                resultList.clear();
//                System.out.println("바디/씁쓸 제거 & 주량늘리기");
//                System.out.println(list.size());
//            }
//            if(i==10){
//                resultList=recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2AndTaste4(
//                        recommendDto.getType(), recommendDto.getStart_degree()-5, recommendDto.getEnd_degree()+5,
//                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
//                        recommendDto.getTaste_1(), recommendDto.getTaste_2(),recommendDto.getTaste_4());
//                removeDup(resultList);
//                System.out.println(resultList.size());
//
//                resultList.clear();
//                System.out.println("씁쓸/담백 제거&주량 늘리기");
//                System.out.println(list.size());
//            }
//

            if (i == 14) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste2(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_2());
                removeDup(resultList);
                System.out.println(resultList.size());

                resultList.clear();
                System.out.println("맛3/맛4/맛5 제거");
                System.out.println(list.size());
            }

            if (i == 15) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste3(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_3());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛2/맛4/맛5 제거");
                System.out.println(list.size());
            }

            if (i == 16) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste3(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_2(), recommendDto.getTaste_3());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛1/맛4/맛5 제거");
                System.out.println(list.size());
            }

            if (i == 17) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛2/맛3/맛4 제거");
                System.out.println(list.size());
            }

            if (i == 18) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_2(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛1/맛3/맛4 제거");
                System.out.println(list.size());
            }

            if (i == 19) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste4AndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_4(), recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛1/맛2/맛3 제거");
                System.out.println(list.size());
            }

            if (i == 20) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste1(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_1());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛1 외 모두 제거");
                System.out.println(list.size());
            }

            if (i == 21) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste2(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_2());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛2 외 모두 제거");
                System.out.println(list.size());
            }

            if (i == 22) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste3(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_3());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛3 외 모두 제거");
                System.out.println(list.size());
            }

            if (i == 23) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste4(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_4());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛4 외 모두 제거");
                System.out.println(list.size());
            }

            if (i == 24) {
                resultList = recommendRepository.findByTypeAndDegreeGreaterThanAndDegreeLessThanAndPriceGreaterThanAndPriceLessThanAndTaste5(
                        recommendDto.getType(), recommendDto.getStart_degree(), recommendDto.getEnd_degree(),
                        recommendDto.getStart_price(), recommendDto.getEnd_price(),
                        recommendDto.getTaste_5());
                removeDup(resultList);
                System.out.println(resultList.size());
                resultList.clear();

                System.out.println("맛5 외 모두 제거");
                System.out.println(list.size());
                break;
            }
            i++;
        }
        return list;
    }
}
