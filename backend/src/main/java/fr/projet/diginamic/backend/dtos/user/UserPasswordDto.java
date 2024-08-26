package fr.projet.diginamic.backend.dtos.user;

public class UserPasswordDto {
    /** The old password of the user */
    private String oldPassword;
    /** The new password of the user */
    private String newPassword;

    /**
     * Constructor
     * 
     * @param oldPassword - the old password of the user
     * @param newPassword - the new password of the user
     */
    public UserPasswordDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

}
