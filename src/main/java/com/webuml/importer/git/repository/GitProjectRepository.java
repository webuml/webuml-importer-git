package com.webuml.importer.git.repository;

import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface GitProjectRepository extends CrudRepository<GitProject, BigInteger> {

}