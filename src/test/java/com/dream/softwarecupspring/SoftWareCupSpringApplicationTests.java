package com.dream.softwarecupspring;

import com.dream.softwarecupspring.utils.AliyunOSSProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@EnableConfigurationProperties(AliyunOSSProperties.class)
@SpringBootTest
class SoftWareCupSpringApplicationTests {

    @Test
    void contextLoads() {
    }

}
