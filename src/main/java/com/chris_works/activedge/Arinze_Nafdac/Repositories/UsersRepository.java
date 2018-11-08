package com.chris_works.activedge.Arinze_Nafdac.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByUserid(long userid);
	Users findByEmail(String email);
}
