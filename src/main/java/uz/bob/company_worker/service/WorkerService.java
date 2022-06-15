package uz.bob.company_worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.bob.company_worker.entity.Address;
import uz.bob.company_worker.entity.Department;
import uz.bob.company_worker.entity.Worker;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.payload.WorkerDto;
import uz.bob.company_worker.repository.AddressRepository;
import uz.bob.company_worker.repository.DepartmentRepository;
import uz.bob.company_worker.repository.WorkerRepository;

import java.util.*;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    Set<Department> departmentSet=new HashSet<>();

    public List<Worker> getWorkers(){
        return workerRepository.findAll();
    }

    public Worker getWorker(Integer id){
        if (!workerRepository.existsById(id)) {
            return null;
        }
        return workerRepository.getReferenceById(id);
    }

    public ApiResponse addWorker(WorkerDto workerDto){
        departmentSet.clear();
        boolean b = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (b)
            return new ApiResponse("This worker already exist",false);
        if (workerRepository.existsByAddress_Id(workerDto.getAddressId())) {
            return new ApiResponse("Its type address already exist other worker please enter other addressId",false);
        }
        if (!addressRepository.existsById(workerDto.getAddressId())) {
            return new ApiResponse("Address not found",false);
        }

        List<Integer> departmentsIds = workerDto.getDepartmentsIds();
        for (Integer integer : departmentsIds) {
            if (!departmentRepository.existsById(integer)) {
                continue;
            }
            departmentSet.add(departmentRepository.getReferenceById(integer));
        }
//        departmentsIds.forEach(integer -> departmentRepository.findById(integer).ifPresent(departmentSet::add));
//        if (departmentSet.isEmpty())
//            return new ApiResponse("departmentSet is empty",false);

        Worker worker=new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(addressRepository.getReferenceById(workerDto.getAddressId()));//change this
        worker.setDepartment(departmentSet);
        workerRepository.save(worker);
        departmentSet.clear();
        return new ApiResponse("Worker added",true);
    }

    public ApiResponse editWorker(Integer id,WorkerDto workerDto){
        departmentSet.clear();
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) {
            return new ApiResponse("Worker not found",false);
        }
        if (workerRepository.existsByAddress_IdAndIdNot(workerDto.getAddressId(),id)) {
            return new ApiResponse("Its type address already exist other worker please enter other addressId",false);
        }
        if (!addressRepository.existsById(workerDto.getAddressId())) {
            return new ApiResponse("Address not found",false);
        }
        boolean b = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (b)
            return new ApiResponse("Phone number already exist at the other worker",false);
        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(addressRepository.getReferenceById(workerDto.getAddressId()));

        List<Integer> departmentsIds = workerDto.getDepartmentsIds();
        departmentsIds.forEach(integer -> departmentRepository.findById(integer).ifPresent(departmentSet::add));
        if (departmentSet.isEmpty())
            return new ApiResponse("departmentSet is empty",false);
        worker.setDepartment(departmentSet);

        workerRepository.save(worker);
        departmentSet.clear();
        return new ApiResponse("Worker successfully edited",true);
    }





}
