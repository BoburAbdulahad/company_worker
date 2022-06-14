package uz.bob.company_worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.company_worker.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    boolean existsByNameAndCompany_Id(String name, Integer company_id);

    boolean existsByNameAndCompany_IdAndIdNot(String name, Integer company_id, Integer id);


}
