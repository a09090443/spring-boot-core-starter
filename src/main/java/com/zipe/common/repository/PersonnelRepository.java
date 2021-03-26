package com.zipe.common.repository;

import com.zipe.employee.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/12/21 下午 04:02
 **/
public interface PersonnelRepository extends JpaRepository<Personnel, String> {

    Personnel findByPnEmpNo(@Param("pnEmpNo") String pnEmpNo);

}
