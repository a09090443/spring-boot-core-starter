package com.zipe.common.repository;

import com.zipe.common.model.SysParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/23 上午 09:56
 **/
@Repository
public interface SysParameterRepository extends JpaRepository<SysParameter, Long> {

    @Query("FROM SysParameter spl WHERE spl.paramKey LIKE %?1%")
    List<SysParameter> findByParamKeyLike(String paramKey);
}
