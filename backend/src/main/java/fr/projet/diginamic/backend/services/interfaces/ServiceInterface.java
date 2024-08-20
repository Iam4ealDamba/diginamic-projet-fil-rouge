package fr.projet.diginamic.backend.services.interfaces;

import java.util.List;

public interface ServiceInterface<T, DTO> {
    public List<T> getAll();

    public T getOne(Long id);

    public List<T> create(DTO userDto);

    public List<T> update(Long id, DTO userDto);

    public List<T> delete(Long id);
}
