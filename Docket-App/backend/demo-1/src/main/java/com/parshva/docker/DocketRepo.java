package com.parshva.docker;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocketRepo extends CrudRepository <DocketTable,String>{
	
}
