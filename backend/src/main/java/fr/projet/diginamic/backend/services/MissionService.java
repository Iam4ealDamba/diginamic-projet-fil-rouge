package fr.projet.diginamic.backend.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.projet.diginamic.backend.dtos.CreateMissionDTO;
import fr.projet.diginamic.backend.dtos.DisplayedMissionDTO;
import fr.projet.diginamic.backend.entities.Mission;
import fr.projet.diginamic.backend.entities.NatureMission;
import fr.projet.diginamic.backend.entities.Expense;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.enums.StatusEnum;
import fr.projet.diginamic.backend.enums.TransportEnum;
import fr.projet.diginamic.backend.mappers.MissionMapper;
import fr.projet.diginamic.backend.repositories.MissionRepository;
import fr.projet.diginamic.backend.specs.MissionSpecifications;
import jakarta.persistence.EntityNotFoundException;

//TODO: reimplement logic of isManager boolean
/**
 * Service class for managing mission entities.
 * Provides methods to perform CRUD operations on mission entities.
 */
@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    UserService userService;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    NatureMissionService natureMissionService;

    @Autowired
    MissionMapper missionMapper;

    /**
     * Save a mission entity.
     * 
     * @param mission the mission to save.
     * @return the saved mission entity.
     */
    public Mission createMission(Mission mission) {
        validateMission(mission, true);
        return missionRepository.save(mission);
    }

    public CreateMissionDTO createMission(CreateMissionDTO dto) {
        Mission bean = missionMapper.fromMissionFormToBean(dto);
        validateMission(bean, true);
        Mission newMissionBean = missionRepository.save(bean);
        CreateMissionDTO newMissionDto = missionMapper.fromBeanToMissionForm(newMissionBean);
        return newMissionDto;
    }

    /**
     * Validates mission data before saving.
     * 
     * @param mission the mission to validate
     * @throws IllegalArgumentException if validation fails
     * @return the mission if no exception were raised
     */
    private void validateMission(Mission mission, boolean isNew) {
        Date today = new Date();
        boolean isManager = true;
        // boolean isManager =
        // SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        // .contains(new SimpleGrantedAuthority("ROLE_MANAGER"));

        // Check if the mission starts in the past or today
        if (!mission.getStartDate().after(today)) {
            if (isNew) {
                throw new IllegalArgumentException("Mission cannot start today or in the past.");
            } else {
                throw new IllegalArgumentException(
                        "Modifications to missions scheduled to start today are not allowed.");
            }
        }

        // Check for 7-day advance booking for flights
        if (mission.getTransport() == TransportEnum.AIRPLANE) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 7);
            if (mission.getStartDate().before(cal.getTime())) {
                throw new IllegalArgumentException("Flights must be booked at least 7 days in advance.");
            }
        }
        // Check valid status for managers and non-managers
        if (isManager) {

            // Check status is either INITIAL or REJECTED for new or modified missions :
            if (mission.getStatus() != StatusEnum.IN_PROGRESS) {
                throw new IllegalArgumentException("Invalid status for operation by manager.");
            }
        } else {
            if (!(mission.getStatus() == StatusEnum.INITIAL || mission.getStatus() == StatusEnum.REJECTED)) {
                throw new IllegalArgumentException("Invalid status for operation by employees.");
            }
        }

        // Check valid nature of mission
        if (mission.getNatureMission().getEndDate() != null && mission.getNatureMission().getEndDate().before(today)) {
            throw new IllegalArgumentException("The nature of the mission is no longer valid.");
        }

        // Ensure start date is before end date
        if (mission.getEndDate().before(mission.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
    }

    /**
     * Retrieve a single mission by its ID.
     * 
     * @param id the ID of the mission to retrieve.
     * @return the found mission entity.
     * @throws EntityNotFoundException if the mission is not found.
     */
    public Mission findOneMission(Long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with ID: " + id));
    }

    /**
     * Retrieve a single mission by its ID.
     * 
     * @param id the ID of the mission to retrieve.
     * @return the found mission entity.
     * @throws EntityNotFoundException if the mission is not found.
     */
    public DisplayedMissionDTO findOneMissionDto(Long id) {
        return missionRepository.findById(id)
                .map(m -> missionMapper.fromBeantoDisplayedMissionDTO(m))
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with ID: " + id));
    }

    /**
     * Retrieve all missions.
     * 
     * @return a list of missions.
     */
    public Page<DisplayedMissionDTO> findAllMissions(Pageable pageable) {
        // TODO: clean
        // return missionRepository.findAll(pageable);
        return missionRepository.findAll(pageable).map(m -> missionMapper.fromBeantoDisplayedMissionDTO(m));
    }

    /**
     * Retrieve all missions that match a given specification.
     * 
     * @param spec     the specification for filtering missions.
     * @param pageable the pagination information.
     * @return a page of missions that match the specification.
     */
    @Transactional(readOnly = true)
    public Page<DisplayedMissionDTO> findAllMissionsWithSpecs(String status, String nature, String labelOrUsername,
            Pageable pageable) {

        // boolean isManager =
        // SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        // .contains(new SimpleGrantedAuthority("ROLE_MANAGER"));

        boolean isManager = true;

        Specification<Mission> spec = isManager ? createSpecificationForManager(status, nature, labelOrUsername)
                : createSpecificationForEmployee(status, nature, labelOrUsername);
        return missionRepository.findAll(spec, pageable).map(m -> missionMapper.fromBeantoDisplayedMissionDTO(m));
    }

    /**
     * Creates a Specification object for filtering missions based on criteria
     * applicable to managers.
     * This method constructs a dynamic query specification that managers can use to
     * filter missions
     * based on the provided status, nature of the mission, and a label or username.
     * Managers have
     * the ability to search missions not only based on mission details but also
     * involving user-related
     * information, providing broader search capabilities.
     *
     * @param status          The status of the missions to be filtered. Can be null
     *                        if status filtering is not required.
     * @param nature          The nature of the missions to be filtered. Can be null
     *                        if nature filtering is not required.
     * @param labelOrUsername The label of the mission or the username of the
     *                        associated user to filter by.
     *                        This allows managers to perform searches across
     *                        mission labels or user names.
     *                        Can be null if this type of filtering is not required.
     * @return A Specification object that can be used to perform the query with the
     *         specified criteria.
     */
    private Specification<Mission> createSpecificationForManager(String status, String nature, String labelOrUsername) {
        return MissionSpecifications.filterMissionsByCriteriaForManager(status, nature, labelOrUsername);
    }

    /**
     * Creates a Specification object for filtering missions based on criteria
     * applicable to employees.
     * This method constructs a dynamic query specification that employees can use
     * to filter missions
     * based on the provided status, nature of the mission, and mission label.
     * Employees are limited
     * to filtering missions based on their own missions, and as such, their filter
     * criteria exclude user-related
     * information beyond their own scope.
     *
     * @param status The status of the missions to be filtered. Can be null if
     *               status filtering is not required.
     * @param nature The nature of the missions to be filtered. Can be null if
     *               nature filtering is not required.
     * @param label  The label of the mission to filter by. This allows employees to
     *               search for specific
     *               missions by label. Can be null if label filtering is not
     *               required.
     * @return A Specification object that can be used to perform the query with the
     *         specified criteria.
     */
    private Specification<Mission> createSpecificationForEmployee(String status, String nature, String label) {
        return MissionSpecifications.filterMissionsByCriteriaForEmployee(status, nature, label);
    }

    /**
     * Retrieve a single mission by its ID and update it.
     * 
     * @param id the ID of the mission to retrieve and update.
     * @param updatedMissionDTO the updated mission data.
     * @return the updated mission DTO.
     * @throws EntityNotFoundException if the mission is not found.
     */
    @Transactional
    public DisplayedMissionDTO updateMission(Long id, DisplayedMissionDTO updatedMission){
        Mission mission = displayedMissionDTOToBean(updatedMission);
        validateMission(mission, false);
        missionRepository.save(mission);
        return missionMapper.fromBeantoDisplayedMissionDTO(mission);
    }

    // TODO: clean
    /**
     * Retrieve a single mission by its ID and update it.
     * 
     * @param id             the ID of the mission to retrieve and update.
     * @param updatedMission the updated mission data.
     * @return the updated mission entity.
     * @throws EntityNotFoundException if the mission is not found.
     */
    // public Mission updateMission(Long id, Mission updatedMission) {
    //     validateMission(updatedMission, false);
    //     return missionRepository.findById(id).map(mission -> {
    //         mission.setStatus(StatusEnum.INITIAL);
    //         mission.setLabel(updatedMission.getLabel());
    //         mission.setTotalPrice(updatedMission.getTotalPrice());
    //         mission.setStartDate(updatedMission.getStartDate());
    //         mission.setEndDate(updatedMission.getEndDate());
    //         mission.setTransport(updatedMission.getTransport());
    //         mission.setDepartureCity(updatedMission.getDepartureCity());
    //         mission.setArrivalCity(updatedMission.getArrivalCity());
    //         mission.setBountyDate(updatedMission.getBountyDate());
    //         mission.setBountyAmount(updatedMission.getBountyAmount());
    //         mission.setUser(updatedMission.getUser());
    //         mission.setNatureMission(updatedMission.getNatureMission());
    //         mission.setExpense(updatedMission.getExpense());
    //         return missionRepository.save(mission);
    //     }).orElseThrow(() -> new EntityNotFoundException("Mission not found with ID: " + id));
    // }

    /**
     * Delete a mission by its ID.
     * 
     * @param id the ID of the mission to delete.
     * @throws EntityNotFoundException if the mission is not found.
     */
    @Transactional
    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new EntityNotFoundException("Mission not found with ID: " + id);
        }
        missionRepository.deleteById(id);
    }

    /**
     * Update a mission by its ID.
     * 
     * @param id the ID of the mission to update.
     * @throws EntityNotFoundException if the mission is not found.
     */
    @Transactional
    public DisplayedMissionDTO updateMissionStatus(Long id, String status) {

        StatusEnum statusEnum;
        try {
            statusEnum = StatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
        return missionRepository.findById(id)
                .map(m -> {
                    validateMission(m, false);
                    m.setStatus(statusEnum);
                    Mission bean = missionRepository.save(m);
                    return missionMapper.fromBeantoDisplayedMissionDTO(bean);
                })
                .orElseThrow(() -> new EntityNotFoundException("Mission not found with ID: " + id));
    }

    public Mission displayedMissionDTOToBean(DisplayedMissionDTO dto) {
        Mission mission = findOneMission(dto.getId());
        mission.setLabel(dto.getLabel());
        mission.setStatus(dto.getStatus());
        mission.setStartDate(dto.getStartDate());
        mission.setEndDate(dto.getEndDate());
        mission.setTransport(dto.getTransport());
        mission.setDepartureCity(dto.getDepartureCity());
        mission.setArrivalCity(dto.getArrivalCity());

        UserEntity user = userService.getOne(dto.getUserId());
        NatureMission natureMisison = natureMissionService.getNatureMissionById(dto.getNatureMissionId());
        Expense expense = expenseService.getExpenseBean(dto.getExpenseId());
        mission.setUser(user);
        mission.setNatureMission(natureMisison);
        mission.setExpense(expense);

        calculatePricing(mission);

        return mission;
    }

    private void calculatePricing(Mission mission) {

        if (mission.getNatureMission() != null) {
            long duration = getDifferenceDays(mission.getStartDate(), mission.getEndDate()) + 1; // Include start day
            double dailyRate = mission.getNatureMission().getTjm();
            double totalPrice = duration * dailyRate;
            mission.setTotalPrice(totalPrice);

            if (mission.getStatus() == StatusEnum.FINISHED && mission.getNatureMission().isEligibleToBounty()) {
                double bountyPercentage = mission.getNatureMission().getBountyPercentage() / 100.0;
                double bountyAmount = totalPrice * bountyPercentage;
                mission.setBountyAmount(bountyAmount);
                mission.setBountyDate(mission.getEndDate());
            }
        }
    }

    private static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}