package com.mindcraft.demo.store.service;

import com.mindcraft.demo.store.entity.Storedetails;
import com.mindcraft.demo.store.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Value("classpath:query.sql")
    private Resource sqlQuery;

    public Storedetails createStore(Storedetails store){
        return storeRepository.save(store);
    }

    public Storedetails updateStore(Storedetails store) {
        Storedetails storeInDatabase = storeRepository.findById(store.getId()).get();
        if(storeInDatabase != null){
            storeInDatabase.setLattitude(store.getLattitude());
            storeInDatabase.setLongitude(store.getLongitude());
            storeInDatabase.setName(store.getName());
            storeInDatabase.setZipcode(store.getZipcode());
            storeRepository.save(storeInDatabase);
        }
        return storeInDatabase;
    }

    public void deleteStore(Long id) {
        Storedetails storeToBeDeleted = storeRepository.findById(id).get();
        if(storeToBeDeleted!=null) {
            storeRepository.delete(storeToBeDeleted);
        }
    }

    public List<Storedetails> getAllStores() {
        return (List<Storedetails>) storeRepository.findAll();
    }

    public List<?> getAllStoresWithinXMiles(double lattitude,double longitude, int xMile) throws IOException {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("lat", lattitude);
        parameters.addValue("long", longitude);
        parameters.addValue("distance", xMile);

        String sql = FileCopyUtils.copyToString(new InputStreamReader(sqlQuery.getInputStream(), UTF_8));
        return jdbcTemplate.queryForList(sql, parameters);
    }
}