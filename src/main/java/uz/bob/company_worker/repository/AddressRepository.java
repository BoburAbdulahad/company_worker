package uz.bob.company_worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.company_worker.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    boolean existsByHomeNumberAndStreet(String homeNumber, String street);

    boolean existsByHomeNumberAndStreetAndIdNot(String homeNumber, String street, Integer id);
}
