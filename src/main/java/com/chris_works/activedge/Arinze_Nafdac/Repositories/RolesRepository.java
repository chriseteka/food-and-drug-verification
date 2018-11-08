package com.chris_works.activedge.Arinze_Nafdac.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
	Roles findByName(String name);
}
