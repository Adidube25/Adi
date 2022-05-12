package com.mindcraft.demo.store.repository;

import com.mindcraft.demo.store.entity.Storedetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Storedetails, Long> {

}
