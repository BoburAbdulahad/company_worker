package uz.bob.company_worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.bob.company_worker.entity.Address;
import uz.bob.company_worker.entity.Company;
import uz.bob.company_worker.payload.AddressDto;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.payload.CompanyDto;
import uz.bob.company_worker.service.AddressService;
import uz.bob.company_worker.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;


    @GetMapping
    public HttpEntity<List<Company>> getAll(){
        return new HttpEntity<>(companyService.getAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<Company> getOneById(@PathVariable Integer id){
        return new HttpEntity<>(companyService.getOneById(id));
    }

    @PostMapping
    public HttpEntity<ApiResponse> add(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse add = companyService.add(companyDto);
        return ResponseEntity.status(add.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(add);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.edit(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse delete = companyService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(delete);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}