package com.example.template.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 20:16
 * @Description:
 */
@NoRepositoryBean
public interface BaseDao<T, I extends Serializable> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {

}
