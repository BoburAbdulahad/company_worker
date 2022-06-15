package uz.bob.company_worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bob.company_worker.entity.Company;
import uz.bob.company_worker.entity.Department;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.payload.DepartmentDto;
import uz.bob.company_worker.repository.CompanyRepository;
import uz.bob.company_worker.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getAll(){
         return departmentRepository.findAll();
    }

    public Department getOneById(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public ApiResponse add(DepartmentDto departmentDto){
        boolean b = departmentRepository.existsByNameAndCompany_Id(departmentDto.getName(), departmentDto.getCompanyId());
        if (b)
            return new ApiResponse("This department already exist its company",false);
        if (!companyRepository.existsById(departmentDto.getCompanyId())) {
            return new ApiResponse("Company not found",false);
        }
        Department department=new Department(
                null,
                departmentDto.getName(),
                companyRepository.getReferenceById(departmentDto.getCompanyId())
        );
        departmentRepository.save(department);
        return new ApiResponse("Department added",true);
    }

    public ApiResponse edit(Integer id,DepartmentDto departmentDto){
        boolean b = departmentRepository.existsByNameAndCompany_IdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (b)
            return new ApiResponse("Its name and companyId already exist other id",false);
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found",false);
        }
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found",false);
        }
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        Department savedDepartment = departmentRepository.save(department);
        return new ApiResponse("Department edited",true,savedDepartment);
    }

    public ApiResponse delete(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found, deleting not successfully",false);
        }
        departmentRepository.delete(optionalDepartment.get());
        return new ApiResponse("Department deleted",true);
    }

}
