package com.mondorevive.TRESPOT.bobina.bobinaStoricoCauzione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BobinaStoricoCauzioneRepository extends JpaRepository<BobinaStoricoCauzione, Long> {
    @Query("select bsc,b,sc from BobinaStoricoCauzione bsc " +
            "inner join bsc.bobina b " +
            "inner join bsc.storicoCauzione sc " +
            "where bsc.storicoCauzione.id in :idStoricoCauzioneList")
    List<BobinaStoricoCauzione> getBobinaStoricoCauzioneByIdStoricoCauzioneList(List<Long> idStoricoCauzioneList);

    @Modifying @Query("delete from BobinaStoricoCauzione bsc where bsc.storicoCauzione.id in " +
            "(select sc.id from StoricoCauzione sc where sc.cauzione.id = :id)")
    void deleteByIdCauzione(Long id);

    @Modifying @Query("delete from BobinaStoricoCauzione bsc where bsc.storicoCauzione.id = :id")
    void deleteByIdStorico(Long id);
}