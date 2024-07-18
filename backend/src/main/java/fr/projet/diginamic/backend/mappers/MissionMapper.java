package fr.projet.diginamic.backend.mappers;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
import fr.projet.diginamic.backend.entities.Mission;

public class MissionMapper {

    public static DisplayedMissionDTO toDTO(Mission mission) {
        DisplayedMissionDTO dto = new DisplayedMissionDTO();
        dto.setId(mission.getId());
        dto.setLabel(mission.getLabel());
        dto.setStatus(mission.getStatus());
        dto.setStartDate(mission.getStartDate());
        dto.setEndDate(mission.getEndDate());
        dto.setTransport(mission.getTransport());
        dto.setDepartureCity(mission.getDepartureCity());
        dto.setArrivalCity(mission.getArrivalCity());
        dto.setBonusDate(mission.getBonusDate());
        dto.setBonusAmount(mission.getBonusAmount());
        dto.setLabelNatureMission(mission.getNatureMission().getLabel());
        dto.setUserId(mission.getUser().getId());
        dto.setNatureMissionId(mission.getNatureMission().getId());
        dto.setExpenseId(mission.getExpense() != null ? mission.getExpense().getId() : null);

        // Calculate the total price
        long duration = getDifferenceDays(mission.getStartDate(), mission.getEndDate());
        double dailyRate = mission.getNatureMission().getTjm();
        dto.setTotalPrice(duration * dailyRate);

        return dto;
    }

    private static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
