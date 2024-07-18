package fr.projet.diginamic.backend.mappers;

import java.util.Date;
import java.util.Optional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.enums.StatusEnum;

public class MissionMapper {

    public static DisplayedMissionDTO toDisplayedMissionDTO(Mission mission) {
        DisplayedMissionDTO dto = new DisplayedMissionDTO();
        dto.setId(mission.getId());
        dto.setLabel(mission.getLabel());
        dto.setStatus(mission.getStatus());
        dto.setStartDate(mission.getStartDate());
        dto.setEndDate(mission.getEndDate());
        dto.setTransport(mission.getTransport());
        dto.setDepartureCity(mission.getDepartureCity());
        dto.setArrivalCity(mission.getArrivalCity());
        dto.setUserId(mission.getUser().getId());
        dto.setNatureMissionId(mission.getNatureMission().getId());
        dto.setExpenseId(mission.getExpense() != null ? mission.getExpense().getId() : null);

        // Calculate the total price based on the daily rate and duration
        long duration = getDifferenceDays(mission.getStartDate(), mission.getEndDate()) + 1; // +1 to include start day
        if (mission.getNatureMission().isBilled()) {
            double dailyRate = mission.getNatureMission().getTjm();
            dto.setTotalPrice(duration * dailyRate);
        } else {
            dto.setTotalPrice(0.0);
        }

        // Set bonus amount if the mission is completed and eligible for a bonus
        if (mission.getStatus() == StatusEnum.FINISHED && mission.getNatureMission().bonus()) {
            double bonusPercentage = mission.getNatureMission().getB

    nusPercentage() / 100.0;
            dto.setBonusAmount(dto.getTotalPrice() * bonusPercentage);
            dto.setBonusDate(mission.getEndDate()); // Bonus date set to the end date of the mission?
        } else {
            dto.setBonusAmount(0.0);
        }
        dto.setLabelNatureMission(mission.getNatureMission().getLabel());
        return dto;
    }

    private static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

    }

    public Mission displayedMissionDTOToBean(DisplayedMissionDTO dto) {
        Mission mission = new Mission();
        mission.setId(dto.getId());
        mission.setLabel(dto.getLabel());
        mission.setStatus(dto.getStatus());
        mission.setStartDate(dto.getStartDate());
        mission.setEndDate(dto.getEndDate());
        mission.setTransport(dto.getTransport());
        mission.setDepartureCity(dto.getDepartureCity());
        mission.setArrivalCity(dto.getArrivalCity());

        // Set User from User ID
        Optional<UserEntity> user = userRepository.findById(dto.getUserId());
        user.ifPresent(mission::setUser);

        // Set NatureMission from NatureMission ID
        Optional<NatureMission> natureMission = natureMissionRepository.findById(dto.getNatureMissionId());
        natureMission.ifPresent(mission::setNatureMission);

        // Set Expense from Expense ID
        Optional<Expense> expense = expenseRepository.findById(dto.getExpenseId());
        expense.ifPresent(mission::setExpense);

        

        return mission;
    }
}
