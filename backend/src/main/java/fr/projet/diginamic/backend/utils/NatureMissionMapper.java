package fr.projet.diginamic.backend.utils;

import org.springframework.stereotype.Service;

import fr.projet.diginamic.backend.dtos.NatureMissionDTO;
import fr.projet.diginamic.backend.entities.NatureMission;

/** Service to transform NatureMission in Dtos and vice-versa */
@Service
public class NatureMissionMapper {

    /**
     * Method to transform a NatureMissionDto to a NatureMission
     * 
     * @param natureMissionDTO, the dto to transform
     * @return the natureMission object after the mapping
     */
    public NatureMission dtoToBean(NatureMissionDTO dto) {
        NatureMission entity = new NatureMission();
        entity.setId(dto.getId());
        entity.setLabel(dto.getLabel());
        entity.setTjm(dto.getCeilingTjm());
        entity.setBilling(dto.getBilling());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setBonusPercentage(dto.getBonusPercentage());
        return entity;
    }

    /**
     * Method to transform a NatureMission to a NatureMissionDto
     * 
     * @param entity, the bean to transform
     * @return the natureMissionDto object after the mapping
     */
    public NatureMissionDTO beanToDto(NatureMission entity) {
        NatureMissionDTO dto = new NatureMissionDTO();
        dto.setId(entity.getId());
        dto.setLabel(entity.getLabel());
        dto.setCeilingTjm(entity.getTjm());
        dto.setBilling(entity.getBilling());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setBonusPercentage(entity.getBonusPercentage());
        return dto;
    }
}
