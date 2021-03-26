package com.zipe.common.service;

import java.util.List;
import java.util.Map;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/26 下午 03:49
 **/
public interface SysMenuService {

    List<Map<String, Object>> findSysMenuByTreeLevel(int treeLevel);

}
