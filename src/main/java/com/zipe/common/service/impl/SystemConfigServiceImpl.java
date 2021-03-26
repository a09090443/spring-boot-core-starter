package com.zipe.common.service.impl;

import com.zipe.base.service.BaseService;
import com.zipe.common.model.SysParameter;
import com.zipe.common.repository.SysParameterRepository;
import com.zipe.common.service.SystemConfigService;
import com.zipe.common.vo.SysParameterVO;
import com.zipe.util.StringConstant;
import com.zipe.util.bean.BeanUtil;
import com.zipe.util.crypto.Base64Util;
import com.zipe.util.crypto.CryptoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/23 上午 09:24
 **/
@Slf4j
@Service
public class SystemConfigServiceImpl extends BaseService implements SystemConfigService {

    private final SysParameterRepository sysParameterRepository;

    @Autowired
    SystemConfigServiceImpl(SysParameterRepository sysParameterRepository) {
        this.sysParameterRepository = sysParameterRepository;
    }

    @Override
    public List<SysParameterVO> selectParametersByKey(String paramKey) {
        List<SysParameter> data = sysParameterRepository.findByParamKeyLike(paramKey);
        return data.stream().map(this::syncToVO).collect(Collectors.toList());
    }

    /**
     * 將資料庫資料轉為畫面所使用的實體
     *
     * @param sysParameter
     * @return
     */
    private SysParameterVO syncToVO(SysParameter sysParameter) {
        CryptoUtil cryptoUtil = new CryptoUtil(new Base64Util());
        SysParameterVO sysParameterVO = new SysParameterVO();

        BeanUtil.copyProperties(sysParameter, sysParameterVO);
        sysParameterVO.setUpdatedBy(sysParameter.getUpdatedBy());
        Optional.ofNullable(sysParameter.getUpdatedAt()).ifPresent(sysParameterVO::setUpdatedAt);
        if (StringUtils.isNotBlank(sysParameter.getIsPassword()) && StringConstant.SHORT_YES.equals(sysParameter.getIsPassword())) {
            sysParameterVO.setParamValue(cryptoUtil.decode(sysParameter.getParamValue()));
        }
        return sysParameterVO;
    }
}
