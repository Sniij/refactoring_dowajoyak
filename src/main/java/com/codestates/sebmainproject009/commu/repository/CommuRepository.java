package com.codestates.sebmainproject009.commu.repository;

import com.codestates.sebmainproject009.commu.entity.Commu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommuRepository  extends JpaRepository<Commu, Long> {

    @Query(value = "SELECT * FROM commu WHERE user_id = :id", nativeQuery = true)
    List<Commu> findCommuListByUserId(@Param("id")Long userId);

}
