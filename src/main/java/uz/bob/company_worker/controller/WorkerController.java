package uz.bob.company_worker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bob.company_worker.entity.Worker;
import uz.bob.company_worker.payload.ApiResponse;
import uz.bob.company_worker.payload.WorkerDto;
import uz.bob.company_worker.service.WorkerService;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping
    public HttpEntity<?> getWorkers(){
        return new HttpEntity<>(workerService.getWorkers());
    }

    @GetMapping("/{id}")
    public HttpEntity<Worker> getWorker(@PathVariable Integer id){
        Worker worker = workerService.getWorker(id);
        if (worker.equals(null))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(worker);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addWorker(@RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editWorker(@PathVariable Integer id,@RequestBody WorkerDto workerDto){
        return null;
    }




}
