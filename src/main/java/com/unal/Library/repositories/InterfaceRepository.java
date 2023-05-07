package com.unal.Library.repositories;


import java.util.List;
import java.util.Optional;

public interface InterfaceRepository<T> {
    /**
     *
     * @return
     */
    public List<T> findAll();

    /**
     *
     * @param id
     * @return
     */
    public Optional<T> findById(int id);

    /**
     *
     * @param newObj
     * @return
     */
    public void save(T newObj);
    /**
     *
     * @param obj
     * @return
     */
    public void edit(T obj);

    /**
     *
     * @param id
     * @return
     */
    public void delete(int id);


}
