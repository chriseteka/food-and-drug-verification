package com.chris_works.activedge.Arinze_Nafdac.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chris_works.activedge.Arinze_Nafdac.Entities.PasswordResetEntity;

public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, Integer> {
	
	PasswordResetEntity findByResetcode(String resetCode);
}
