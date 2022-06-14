package uz.bob.company_worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bob.company_worker.entity.Address;
import uz.bob.company_worker.payload.AddressDto;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAll(){
        List<Address> all = addressRepository.findAll();
        return all;
    }

    public Address getOneById(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }
    public ApiResponse add(AddressDto addressDto){
        boolean b = addressRepository.existsByHomeNumberAndStreet(addressDto.getHomeNumber(), addressDto.getStreet());
        if (b)
            return new ApiResponse("Its street and home number already exist",false);
        Address address=new Address(
                null,
                addressDto.getStreet(),
                addressDto.getHomeNumber()
        );
        addressRepository.save(address);
        return new ApiResponse("Address added",true);
    }

    public ApiResponse edit(Integer id,AddressDto addressDto){
        boolean b = addressRepository.existsByHomeNumberAndStreetAndIdNot(addressDto.getHomeNumber(), addressDto.getStreet(), id);
        if (b)
            return new ApiResponse("This type street and home number exist other address id!",false);
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Address not found",false);
        }
        Address editingAddress = optionalAddress.get();
        editingAddress.setStreet(addressDto.getStreet());
        editingAddress.setHomeNumber(addressDto.getHomeNumber());
        Address savedAddress = addressRepository.save(editingAddress);
        return new ApiResponse("Address edited",true,savedAddress);
    }

    public ApiResponse delete(Integer id){
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address deleted",true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }




}
