package com.mit.repository;

import com.mit.domain.POFER03Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface IPOFER03ClientRepository extends JpaRepository<POFER03Client, String> {

    @Query("SELECT p FROM POFER03Client p WHERE p.idClient = :idClient AND p.creationDate >= :creationDate AND p.updateDate <= :updateDate")
    List<POFER03Client> findClientsWithinDateRange(@Param("idClient") String idClient, @Param("creationDate") Date creationDate, @Param("updateDate") Date updateDate);


    @Query(value = "SELECT SUM(CASE WHEN ST_ACCEPTED = 1 THEN 1 ELSE 0 END) AS accepted, \n" +
            "       SUM(NU_NET_AMOUNT) AS amount \n" +
            "FROM POfer03_CLIENT WHERE CD_IDCLIENT = ?", nativeQuery = true)
    Map<String, Object> getCountAndSum(@Param("idClient") String idClient);

    boolean existsByIdAndAccepted(String id, Integer accepted);
}
