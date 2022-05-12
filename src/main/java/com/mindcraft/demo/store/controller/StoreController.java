package com.mindcraft.demo.store.controller;

import com.mindcraft.demo.store.service.StoreService;
import com.mindcraft.demo.store.entity.Storedetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    StoreService storeService;

    @PostMapping
    public Storedetails creatStore(@RequestBody Storedetails store) {
        return storeService.createStore(store);
    }

	@PatchMapping
	public Storedetails updateStore(@RequestBody Storedetails store){
		return storeService.updateStore(store);
	}

    @DeleteMapping
    public void deleteStore(@RequestParam("id") Long id){
        storeService.deleteStore(id);
    }

//    @GetMapping
//    public List<Storedetails> getStores(){
//        return storeService.getAllStores();
//    }

    @GetMapping(value = "/getStore")
    public List<?> getAllStoresWithinXmiles(@RequestParam("lat")double lat, @RequestParam("long")double longitude, @RequestParam("distance")int distance) throws IOException {
        return storeService.getAllStoresWithinXMiles(lat, longitude, distance);
    }
}