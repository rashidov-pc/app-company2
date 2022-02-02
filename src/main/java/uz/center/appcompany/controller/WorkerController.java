package uz.center.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.center.appcompany.entity.Department;
import uz.center.appcompany.entity.Worker;
import uz.center.appcompany.payload.ApiResponse;
import uz.center.appcompany.payload.DepartmentDto;
import uz.center.appcompany.payload.WorkerDto;
import uz.center.appcompany.service.DepartmentService;
import uz.center.appcompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public HttpEntity<?> getWorkers(){
        List<Worker> workers = workerService.getWorkers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{id}")
    public HttpEntity<Worker> getWorkerById(@PathVariable Integer id){
        Worker worker = workerService.getWorkerById(id);
        return ResponseEntity.ok(worker);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addCustomer(@Valid @RequestBody WorkerDto dto){
        ApiResponse response = workerService.addWorker(dto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto dto){
        ApiResponse response = workerService.editWorker(id, dto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorker(@PathVariable Integer id){
        ApiResponse response = workerService.deleteWorker(id);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
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
