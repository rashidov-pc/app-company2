package uz.center.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.center.appcompany.entity.Address;
import uz.center.appcompany.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}
