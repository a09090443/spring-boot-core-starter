package com.zipe.common.service.impl;

import com.zipe.base.service.BaseService;
import com.zipe.common.jdbc.SysMenuJDBC;
import com.zipe.common.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/26 下午 03:51
 **/
@Slf4j
@Service
public class SysMenuServiceImpl extends BaseService implements SysMenuService {

    private final SysMenuJDBC sysMenuJDBC;

    @Autowired
    SysMenuServiceImpl(SysMenuJDBC sysMenuJDBC){
        this.sysMenuJDBC = sysMenuJDBC;
    }

    @Override
    public List<Map<String, Object>> findSysMenuByTreeLevel(int treeLevel) {
        return sysMenuJDBC.findByLevel(treeLevel);
    }
}
