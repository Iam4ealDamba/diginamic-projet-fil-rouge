package fr.projet.diginamic.backend.scheduling;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.enums.StatusEnum;
import fr.projet.diginamic.backend.repositories.MissionRepository;
import fr.projet.diginamic.backend.specs.MissionSpecifications;
import fr.projet.diginamic.backend.utils.CalculateMissionPricing;

@Component
public class MissionUpdater {

    @Autowired
    MissionRepository missionRepository;


     /**
     * Scheduled method to update mission statuses. 
     * This method runs at midnight every day.
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    protected void updateMissionStatus(){
        System.out.println("[CRON: updateMissionStatus] STARTING...");
        List<Mission> initialMissions = missionRepository.getAllByStatus(StatusEnum.INITIAL);
        System.out.println("[CRON: updateMissionStatus] FOUND " + initialMissions.size() +  "MISSIONS TO UPDATE TO WAITING STATUS");
        
        initialMissions.forEach(mission -> {
            try {
                mission.setStatus(StatusEnum.WAITING);
                System.out.println("[CRON: updateMissionStatus] Updated mission with ID: " + mission.getId());
            } catch (Exception e) {
                System.err.println("[CRON: updateMissionStatus] Error updating mission with ID: " + mission.getId() + " :" + e);
            }
        });

        missionRepository.saveAll(initialMissions);
    } 

    /**
	 * Calculate the bounty amount and the total price of the missions if they are completed and eligible for a bounty.
     * This method runs at midnight every day.
	 */
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    protected void updateMissionPricing(){  
        System.out.println("[CRON: updateMissionPricing]STARTING... ");
        List<Mission> missions = missionRepository.findAll(MissionSpecifications.missionEligibleForBountyToCalculate());
        System.out.println("[CRON: updateMissionPricing]FOUND " + missions.size() + "MISSIONS TO UPDATE");
        
        missions.forEach(m -> {
            try {
                CalculateMissionPricing.calculateBounty(m);
                System.out.println("[CRON: updateMissionPricing] Updated mission with ID: " + m.getId());
            } catch (Exception e) {
                System.err.println("[CRON: updateMissionPricing] ERROR: FAILED TO UPDATE MISSION WITH ID: " + m.getId() + " :" + e);
            }
        });
        missionRepository.saveAll(missions);
        System.out.println("[CRON: updateMissionPricing] FINISHED.");
    }

    // @Scheduled(cron = "* * * * * ?")
    // // @Scheduled(fixedRate = 1)
    // public void testCron(){
    //     System.out.println("TEST CRON:::::::::::");
    // }

}
