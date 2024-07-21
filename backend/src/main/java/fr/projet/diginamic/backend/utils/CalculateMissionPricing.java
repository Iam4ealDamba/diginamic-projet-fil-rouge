package fr.projet.diginamic.backend.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.enums.StatusEnum;

@Service
public class CalculateMissionPricing {

        public void calculatePricing(Mission mission) {

        if (mission.getNatureMission() != null) {
            long duration = getDifferenceDays(mission.getStartDate(), mission.getEndDate()); 
            double dailyRate = mission.getNatureMission().getAdr();
            double totalPrice = duration * dailyRate;
            mission.setTotalPrice(totalPrice);

            if (mission.getStatus() == StatusEnum.FINISHED && mission.getNatureMission().getIsEligibleToBounty()) {
                double bountyPercentage = mission.getNatureMission().getBountyPercentage() / 100.0;
                double bountyAmount = totalPrice * bountyPercentage;
                mission.setBountyAmount(bountyAmount);
                mission.setBountyDate(mission.getEndDate());
            }
        }
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;// Include start day
    }
    
}
