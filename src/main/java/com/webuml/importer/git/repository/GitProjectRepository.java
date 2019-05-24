package com.webuml.importer.git.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface GitProjectRepository extends CrudRepository<GitProject, BigInteger> {

}