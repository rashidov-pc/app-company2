package uz.center.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.center.appcompany.entity.Address;
import uz.center.appcompany.entity.Company;
import uz.center.appcompany.entity.Department;
import uz.center.appcompany.entity.Worker;
import uz.center.appcompany.payload.ApiResponse;
import uz.center.appcompany.payload.DepartmentDto;
import uz.center.appcompany.payload.WorkerDto;
import uz.center.appcompany.repository.AddressRepository;
import uz.center.appcompany.repository.CompanyRepository;
import uz.center.appcompany.repository.DepartmentRepository;
import uz.center.appcompany.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    WorkerRepository workerRepository;

    public List<Worker> getWorkers() {
        List<Worker> workerList = workerRepository.findAll();
        return workerList;
    }

    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    public ApiResponse addWorker(WorkerDto dto) {
        boolean existsByName = workerRepository.existsByNameAndPhoneNumber(dto.getName(), dto.getPhoneNumber());
        if (existsByName) {
            return new ApiResponse("Ushbu ishchi avval ro`yxatdan o`tgan", false);
        }
        Worker worker = new Worker();
        worker.setName(dto.getName());
        worker.setPhoneNumber(dto.getPhoneNumber());

        Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Bunday Department mavjud emas", false);
        }
        boolean existsByPhoneNumberAndDepartmentId = workerRepository.existsByPhoneNumberAndDepartmentId(dto.getPhoneNumber(), dto.getDepartmentId());
        if (existsByPhoneNumberAndDepartmentId) {
            return new ApiResponse("Ushbu ishchi ushbu departmentda mavjud", false);
        }
        Department department = optionalDepartment.get();
        worker.setDepartment(department);

        Address address = new Address();
        address.setHomeNumber(dto.getHomeNumber());
        address.setStreet(dto.getStreet());
        Address saveAddress = addressRepository.save(address);
        worker.setAddress(saveAddress);
        workerRepository.save(worker);
        return new ApiResponse("Department saved", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto dto) {
        boolean existsByName = workerRepository.existsByNameAndPhoneNumberAndIdNot(dto.getName(), dto.getPhoneNumber(), id);
        if (existsByName) {
            return new ApiResponse("Bunday ishchi mavjud", false);
        }

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) {
            return new ApiResponse("Bunday ishchi mavjud emas", false);
        }
        Worker worker = optionalWorker.get();
        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return new ApiResponse("Bunday telefon raqam ilgari ro`yxatdan o`tgan", false);
        }
        worker.setName(dto.getName());
        worker.setPhoneNumber(dto.getPhoneNumber());

        Optional<Department> optionalDepartment = departmentRepository.findById(dto.getDepartmentId());

        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Bunday department mavjud emas", false);
        }

        worker.setDepartment(optionalDepartment.get());
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setHomeNumber(dto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);
        worker.setAddress(savedAddress);
        workerRepository.save(worker);
        return new ApiResponse("Edit department", true);
    }

    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
}
