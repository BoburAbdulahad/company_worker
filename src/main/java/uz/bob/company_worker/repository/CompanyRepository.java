package uz.bob.company_worker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.company_worker.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    boolean existsByCorpNameAndDirectorName(String corpName, String directorName);

    boolean existsByCorpNameAndDirectorNameAndIdNot(String corpName, String directorName, Integer id);

    boolean existsByAddress_Id(Integer address_id);

}
