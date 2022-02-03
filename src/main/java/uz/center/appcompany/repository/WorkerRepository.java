package uz.center.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.center.appcompany.entity.Address;
import uz.center.appcompany.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);
    boolean existsByNameAndPhoneNumberAndIdNot(String name, String phoneNumber, Integer id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
    boolean existsByPhoneNumberAndDepartmentId(String phoneNumber, Integer department_id);
}
