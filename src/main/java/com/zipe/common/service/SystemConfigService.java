package com.zipe.common.service;

import com.zipe.common.vo.SysParameterVO;

import java.util.List;

public interface SystemConfigService {

    List<SysParameterVO> selectParametersByKey(String paramKey);

}
