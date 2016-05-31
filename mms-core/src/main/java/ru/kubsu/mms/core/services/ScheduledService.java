package ru.kubsu.mms.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kubsu.mms.core.db.models.MetroControl;
import ru.kubsu.mms.core.db.models.TechControl;
import ru.kubsu.mms.core.db.repo.MetroControlRepo;
import ru.kubsu.mms.core.db.repo.StatusRepo;
import ru.kubsu.mms.core.db.repo.TechControlRepo;
import sun.util.resources.cldr.ta.CalendarData_ta_IN;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DZRock on 17.05.2016.
 */
@Service
public class ScheduledService {


    @Qualifier("techControlRepo")
    @Autowired
    private TechControlRepo techControlRepo;
    @Qualifier("metroControlRepo")
    @Autowired
    private MetroControlRepo metroControlRepo;
    @Qualifier("statusRepo")
    @Autowired
    private StatusRepo statusRepo;


    @Scheduled(cron = "0 0 1 * * *")
    private void generateControls(){
        List<TechControl> tec = (List<TechControl>) techControlRepo.findAll();
        for(TechControl tc:tec){
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            Calendar tcCheckoutDate = Calendar.getInstance();
            tcCheckoutDate.setTime(tc.getLastSupportdate());
            tcCheckoutDate.add(Calendar.DAY_OF_YEAR,tc.getPeriod());

            if((now.get(Calendar.DAY_OF_YEAR)>tcCheckoutDate.get(Calendar.DAY_OF_YEAR))
            && (now.get(Calendar.YEAR)>=tcCheckoutDate.get(Calendar.YEAR))){
                tc.setStatus(statusRepo.findBySystemName("need_checkout"));
            }

        }
        techControlRepo.save(tec);

        List<MetroControl> mec = (List<MetroControl>) metroControlRepo.findAll();
        for(MetroControl mc:mec){
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            Calendar mcCheckoutDate = Calendar.getInstance();
            mcCheckoutDate.setTime(mc.getLastSupportdate());
            mcCheckoutDate.add(Calendar.DAY_OF_YEAR,mc.getPeriod());

            if((now.get(Calendar.DAY_OF_YEAR)>mcCheckoutDate.get(Calendar.DAY_OF_YEAR))
                    && (now.get(Calendar.YEAR)>=mcCheckoutDate.get(Calendar.YEAR))){
                mc.setStatus(statusRepo.findBySystemName("need_checkout"));
            }
        }
        metroControlRepo.save(mec);
    }

}
