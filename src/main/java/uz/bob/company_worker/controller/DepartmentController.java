package uz.bob.company_worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bob.company_worker.entity.Department;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.payload.DepartmentDto;
import uz.bob.company_worker.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public HttpEntity<List<Department>> getAll() {
        List<Department> list = departmentService.getAll();
        return new HttpEntity<>(list);
    }

//    @GetMapping("/{number}")todo check this method
    //    public HttpEntity<Department> get(@PathVariable(value = "number") Integer id){
//        Department department = departmentService.getOneById(id);
//        if (department==null)
//            return ResponseEntity.status(409).body(null);
//        return ResponseEntity.status(HttpStatus.OK).body(department);
//    }
    @GetMapping("/{id}")
    public HttpEntity<Department> getDepartment(@PathVariable Integer id) {
        Department department = departmentService.getOneById(id);
        return new HttpEntity<>(department);
    }

    @PostMapping
    public HttpEntity<ApiResponse> add(@RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.add(departmentDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@RequestBody DepartmentDto departmentDto){
        ApiResponse response = departmentService.edit(id, departmentDto);
        return ResponseEntity.status(response.isSuccess()?202:409).body(response);
    }

    @DeleteMapping("/{num}")
    public HttpEntity<?> delete(@PathVariable(value = "num") Integer id){
        ApiResponse apiResponse = departmentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }


}
