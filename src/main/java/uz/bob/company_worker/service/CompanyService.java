package uz.bob.company_worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bob.company_worker.entity.Address;
import uz.bob.company_worker.entity.Company;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.payload.CompanyDto;
import uz.bob.company_worker.repository.AddressRepository;
import uz.bob.company_worker.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;


    public List<Company> getAll(){
       return companyRepository.findAll();
    }

    public Company getOneById(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    public ApiResponse add(CompanyDto companyDto){
        boolean b = companyRepository.existsByCorpNameAndDirectorName(companyDto.getCorpName(), companyDto.getDirectorName());
        if (b)
            return new ApiResponse("Its corparation name to have director name,pleace change one or more!",false);
        if (!addressRepository.existsById(companyDto.getAddressId()))
            return new ApiResponse("Address not found",false);
        boolean existsByAddress_id = companyRepository.existsByAddress_Id(companyDto.getAddressId());
        if (existsByAddress_id)
            return new ApiResponse("Address already exist please enter other addressId",false);
        Company company=new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        company.setAddress(addressRepository.getReferenceById(companyDto.getAddressId()));
        companyRepository.save(company);
        return new ApiResponse("Company added",true);
    }

    public ApiResponse edit(Integer id,CompanyDto companyDto){
        boolean b = companyRepository.existsByCorpNameAndDirectorNameAndIdNot(companyDto.getCorpName(), companyDto.getDirectorName(), id);
        if (b)
            return new ApiResponse("This corpName and directorName already exist other id",false);
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found",false);
        }
        boolean b1 = companyRepository.existsByAddress_Id(companyDto.getAddressId());
        if (b1) {
            return new ApiResponse("Address already exist",false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Address not found",false);
        }
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        Company savedCompany = companyRepository.save(company);
        return new ApiResponse("Company edited",true,savedCompany);
    }

    public ApiResponse delete(Integer id){
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company deleted",true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting",false);
        }
    }


}
