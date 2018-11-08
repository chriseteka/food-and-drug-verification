package com.chris_works.activedge.Arinze_Nafdac.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Admins;

public interface AdminsRepository extends JpaRepository<Admins, Integer>{

	Admins findByAdminmail(String adminMail);
}
