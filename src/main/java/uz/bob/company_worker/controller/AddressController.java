package uz.bob.company_worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bob.company_worker.entity.Address;
import uz.bob.company_worker.payload.AddressDto;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public HttpEntity<List<Address>> get(){
        return ResponseEntity.ok(addressService.getAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<Address> getOneById(@PathVariable Integer id){
        return null;
    }
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody AddressDto addressDto){
        return ResponseEntity.ok(addressService.add(addressDto));
    }

    @PutMapping("/{id}")
    public ApiResponse edit(@PathVariable Integer id, @RequestBody AddressDto addressDto){
return null;
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return null;
    }


}
