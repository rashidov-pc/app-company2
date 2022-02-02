package uz.center.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.center.appcompany.entity.Company;
import uz.center.appcompany.entity.Department;
import uz.center.appcompany.payload.ApiResponse;
import uz.center.appcompany.payload.DepartmentDto;
import uz.center.appcompany.repository.CompanyRepository;
import uz.center.appcompany.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }


    public ApiResponse addDepartment(DepartmentDto dto) {
        boolean existsByName = departmentRepository.existsByNameAndCompanyId(dto.getName(), dto.getCompanyId());
        if (existsByName){
            return new ApiResponse("Bunday department mavjud", false);
        }
        Department department = new Department();
        department.setName(dto.getName());

        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
        if (!optionalCompany.isPresent()){
            return new ApiResponse("Bunday kompaniya mavjud emas", false);
        }

        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department saved", true);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto dto) {
        boolean existsByName = departmentRepository.existsByNameAndIdNotAndCompanyId(dto.getName(), id, dto.getCompanyId());
        if (existsByName){
            return new ApiResponse("Bunday department mavjud", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()){
            return new ApiResponse("Bunday department mavjud emas", false);
        }

        Department department = optionalDepartment.get();
        department.setName(dto.getName());

        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
        if (!optionalCompany.isPresent()){
            return new ApiResponse("Bunday kompaniya mavjud emas", false);
        }
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Edit department", true);
    }

    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Deleted", true);
        }catch (Exception e){
            return new ApiResponse("Error", false);
        }
    }
}
