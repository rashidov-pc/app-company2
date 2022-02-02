package uz.center.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
//        boolean existsByName = departmentRepository.existsByNameAndCompanyId(dto.getName(), dto.getCompanyId());
//        if (existsByName){
//            return new ApiResponse("Bunday department mavjud", false);
//        }
//        Department department = new Department();
//        department.setName(dto.getName());
//
//        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
//        if (!optionalCompany.isPresent()){
//            return new ApiResponse("Bunday kompaniya mavjud emas", false);
//        }
//
//        department.setCompany(optionalCompany.get());
//        departmentRepository.save(department);
//        return new ApiResponse("Department saved", true);
        return null;
    }

    public ApiResponse editWorker(Integer id, WorkerDto dto) {
//        boolean existsByName = departmentRepository.existsByNameAndIdNotAndCompanyId(dto.getName(), id, dto.getCompanyId());
//        if (existsByName){
//            return new ApiResponse("Bunday department mavjud", false);
//        }
//
//        Optional<Department> optionalDepartment = departmentRepository.findById(id);
//        if (!optionalDepartment.isPresent()){
//            return new ApiResponse("Bunday department mavjud emas", false);
//        }
//
//        Department department = optionalDepartment.get();
//        department.setName(dto.getName());
//
//        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
//        if (!optionalCompany.isPresent()){
//            return new ApiResponse("Bunday kompaniya mavjud emas", false);
//        }
//        department.setCompany(optionalCompany.get());
//        departmentRepository.save(department);
//        return new ApiResponse("Edit department", true);
        return null;
    }

    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }
}
