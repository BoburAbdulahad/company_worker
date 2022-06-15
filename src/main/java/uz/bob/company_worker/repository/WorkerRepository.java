package uz.bob.company_worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.company_worker.entity.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker,Integer> {

    //for add
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByAddress_Id(Integer address_id);

    //for edit
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

    boolean existsByAddress_IdAndIdNot(Integer address_id, Integer id);


}
