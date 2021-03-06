package uz.bob.company_worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.bob.company_worker.entity.Address;
import uz.bob.company_worker.payload.AddressDto;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Address oneById = addressService.getOneById(id);
        return ResponseEntity.ok(oneById);
    }
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody AddressDto addressDto){
        ApiResponse add = addressService.add(addressDto);
        return ResponseEntity.status(add.isSuccess()?201:409).body(add);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> edit(@PathVariable Integer id,@Valid @RequestBody AddressDto addressDto){

        ApiResponse edit = addressService.edit(id, addressDto);
        if (edit.isSuccess())
            return ResponseEntity.status(202).body(edit);
        return ResponseEntity.status(409).body(edit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id){
        ApiResponse delete = addressService.delete(id);
        return ResponseEntity.status(delete.isSuccess()?204:409).body(delete);
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
