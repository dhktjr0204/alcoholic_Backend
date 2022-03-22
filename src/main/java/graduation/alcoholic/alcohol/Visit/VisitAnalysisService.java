package graduation.alcoholic.alcohol.Visit;

import graduation.alcoholic.alcohol.AlcoholRepository;
import graduation.alcoholic.domain.Alcohol;
import graduation.alcoholic.domain.User;
import graduation.alcoholic.domain.Visit;
import graduation.alcoholic.login.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VisitAnalysisService {

    private final UserRepository userRepository;
    private final String basePath = "./logs/logback-";
    private final VisitRepository visitRepository;
    private final AlcoholRepository alcoholRepository;


   // @Scheduled(cron = "0 0/5 * * * *") //5분마다 실행
   // @Scheduled(cron = "0 0 0/1 * * *") //1시간마다실행
   // @Scheduled(fixedDelay = 10000)
    @Scheduled(cron = "0 0 0 * * *") //매일 자정
    public void readLogFile () throws IOException {
        Date d = new Date(); //오늘날짜
        d = new Date(d.getTime()+(1000*60*60*24*-1)); //어제날짜
        SimpleDateFormat yesterday = new SimpleDateFormat("yyyy-MM-dd");

        String filePath = basePath+ yesterday.format(d)+".0.log";
        List<String> lines = Files.readAllLines(Paths.get(filePath),
                StandardCharsets.UTF_8); //어제 날짜의 로그파일 읽어들임

        for(String line: lines) {
            Map<String, Long> userIdAndAlcoholId = getUserIdAndAlcoholId(line);
            //Alcohol alcohol = boardRepository.findById(userIdAndAlcoholId.get("a_id")).orElseThrow();
            Long a_id = userIdAndAlcoholId.get("a_id");
            Long u_id = userIdAndAlcoholId.get("u_id");

            Map<String, Object> ageRangeAndSex = getUserAgeRangeAndSex(u_id);
            String ageRange = ageRangeAndSex.get("ageRange").toString();
            String sex = ageRangeAndSex.get("sex").toString();

            save(a_id,ageRange,sex);

//            System.out.println("u_id= "+userIdAndAlcoholId.get("u_id"));
//            System.out.println("a_id= "+userIdAndAlcoholId.get("a_id"));
//            System.out.println("ageRange= "+ageRangeAndSex.get("ageRange"));
//            System.out.println("sex= "+ageRangeAndSex.get("sex"));
        }

    }

    public Map<String, Long> getUserIdAndAlcoholId (String line) {
        String[] split = line.split(":");
        Long u_id = Long.parseLong(split[3].split(" ")[1]);
        Long a_id = Long.parseLong(split[4].split(" ")[1]);

        Map<String, Long> res = new HashMap<>();
        res.put("u_id",u_id);
        res.put("a_id",a_id);

        return res;
    }

    public Map<String, Object> getUserAgeRangeAndSex (Long u_id) {
        Optional<User> user = userRepository.findById(u_id);
        Map<String, Object> res = new HashMap<>();
        res.put("ageRange", user.get().getAge_range());
        res.put("sex",user.get().getSex());
        return res;
    }

    public void save (Long a_id, String ageRange, String sex) {
        //VisitSaveDto saveDto = new VisitSaveDto(a_id);
        Alcohol a = alcoholRepository.findById(a_id).orElseThrow();
        Visit visitEntity = visitRepository.findById(a_id).orElse(new Visit(a,0L,0L,0L,0L,0L,0L));

        if (sex.equals("female")) {
            visitEntity.updateFemale();
        }
        else if (sex.equals("male")) {
            visitEntity.updateMale();
        }

        if (ageRange.equals("20~29")) {
            visitEntity.updateTwentys();
        }
        else if (ageRange.equals("30~39")) {
            visitEntity.updateThirtys();
        }
        else if (ageRange.equals("40~49")) {
            visitEntity.updateFourtys();
        }
        else {
            visitEntity.updateFiftys();
        }

        visitRepository.save(visitEntity);
    }

    public VisitDto getVisitInfo (Long a_id) {
        Visit visitEntity = visitRepository.findById(a_id).get();
        VisitDto visitDto = new VisitDto(visitEntity);

        return visitDto;
    }
}
