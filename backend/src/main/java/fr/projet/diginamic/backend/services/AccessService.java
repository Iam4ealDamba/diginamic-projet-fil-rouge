package fr.projet.diginamic.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.projet.diginamic.backend.repositories.interfaces.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import fr.projet.diginamic.backend.entities.UserEntity;
import fr.projet.diginamic.backend.entities.Mission;

/**
 * Service class responsible for handling access control logic
 * for different types of users based on their roles and relationships.
 */
@Service
public class AccessService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private MissionService missionService;

    /**
     * Determines whether the currently authenticated user has access
     * to resources associated with the specified user ID.
     *
     * @param userId the ID of the user whose resources are being accessed
     * @return true if the current user has access, false otherwise
     * @throws EntityNotFoundException if the current user or the target user is not found
     */
    public boolean hasAccess(String currentUserEmail, Long targetUserId) {
        
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (isAdmin(currentUser) || isCurrentUser(currentUser, targetUserId) || isManagerOfUser(currentUser, targetUserId)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the current user has read access to a specific mission.
     * 
     * This method determines if the current user can view the details of the mission specified by the missionId.
     * Access is granted if the user is an admin, the owner of the mission, or the manager of the mission's owner.
     *
     * @param missionId the ID of the mission to check access for
     * @param currentUserEmail the email of the current user
     * @return true if the user has read access to the mission, false otherwise
     * @throws EntityNotFoundException if the user or mission is not found
     */
    public boolean hasReadAccessToMission(Long missionId, String currentUserEmail) {
        
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
            Mission mission = missionService.findOneMission(missionId);
            Long missionOwnerId = mission.getUser().getId();

        if (isAdmin(currentUser) || isCurrentUser(currentUser, missionOwnerId) || isManagerOfUser(currentUser, missionOwnerId)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the current user has read and write access to a specific mission.
     * 
     * This method determines if the current user can view and modify the details of the mission specified by the missionId.
     * Access is granted if the user is an admin or the owner of the mission.
     *
     * @param missionId the ID of the mission to check access for
     * @param currentUserEmail the email of the current user
     * @return true if the user has read and write access to the mission, false otherwise
     * @throws EntityNotFoundException if the user or mission is not found
     */
    public boolean hasReadWriteAccessToMission(Long missionId, String currentUserEmail) {
        
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
            Mission mission = missionService.findOneMission(missionId);
            Long missionOwnerId = mission.getUser().getId();

        if (isAdmin(currentUser) || isCurrentUser(currentUser, missionOwnerId)) {
            return true;
        }
        return false;
    }

     /**
     * Checks if the current user has admin or manager privileges for a specific mission.
     * 
     * This method determines if the current user can manage the mission specified by the missionId.
     * Access is granted if the user is an admin or the manager of the mission's owner.
     *
     * @param missionId the ID of the mission to check access for
     * @param currentUserEmail the email of the current user
     * @return true if the user has admin or manager privileges for the mission, false otherwise
     * @throws EntityNotFoundException if the user or mission is not found
     */
    public boolean hasAdminOrManagerPrivilegesForMission(Long missionId, String currentUserEmail) {
        
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
            Mission mission = missionService.findOneMission(missionId);
            Long missionOwnerId = mission.getUser().getId();

        if (isAdmin(currentUser) || isManagerOfUser(currentUser, missionOwnerId)) {
            return true;
        }
        return false;
    }

    // General custom "getters" to combine conditions:
   /**
     * Checks if the given user has the admin role.
     *
     * @param user the user entity to check
     * @return true if the user has the admin role, false otherwise
     */
    public boolean isAdmin(UserEntity user) {
        return "ADMIN".equals(user.getRole().getType());
    }

    public boolean isAdmin(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return "ADMIN".equals(user.getRole().getType());
    }

    /**
     * Checks if the current user is the same as the user specified by the userId.
     *
     * @param currentUser the currently authenticated user
     * @param targetUserId the ID of the user to check against
     * @return true if the current user is the same as the user specified by the userId, false otherwise
     */
    public boolean isCurrentUser(UserEntity currentUser, Long targetUserId) {
        return currentUser.getId().equals(targetUserId);
    }

    /**
     * Checks if the current user is the manager of the user specified by the userId.
     *
     * @param currentUser the currently authenticated user
     * @param targetUserId the ID of the user whose manager is to be checked
     * @return true if the current user is the manager of the user specified by the userId, false otherwise
     * @throws EntityNotFoundException if the target user is not found
     */
    public boolean isManagerOfUser(UserEntity currentUser, Long targetUserId) {
        UserEntity targetUser = userRepository.findById(targetUserId)
            .orElseThrow(() -> new EntityNotFoundException("Target user not found"));
       
            if(targetUser.getManager() == null){
            return false;
        }
        return currentUser.getId().equals(targetUser.getManager().getId());
    }
}
