package com.example.repository;

import com.example.model.auth.Staff;
import com.example.model.dto.authDTO.StaffInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("SELECT NEW com.example.model.dto.authDTO.StaffInfoDTO (" +
            "st.fullName, " +
            "st.email" +
            ") " +
            "FROM Staff AS st " +
            "JOIN User AS us " +
            "ON st.user = us " +
            "AND us.username = :username"
    )
    Optional<StaffInfoDTO> getStaffInfoByUserName(@Param("username")String username);

    Boolean existsByEmail(String email);
}
