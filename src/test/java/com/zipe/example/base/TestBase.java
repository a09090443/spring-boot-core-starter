package com.zipe.example.base;

import com.zipe.base.config.AppConfig;
import com.zipe.common.config.DataSourceConfig;
import com.zipe.common.config.quartz.QuartzConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/19 下午 01:42
 **/
@Slf4j
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class, DataSourceConfig.class, QuartzConfig.class })
public class TestBase {
}
