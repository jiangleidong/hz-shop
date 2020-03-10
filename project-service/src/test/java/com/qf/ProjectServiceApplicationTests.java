package com.qf;

import com.qf.entity.TUser;
import com.qf.mapper.TUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectServiceApplicationTests {
    @Autowired
    private TUserMapper mapper;

    @Test
    void contextLoads() {
        long a=2;
        TUser tUser = mapper.selectByPrimaryKey(a);
        System.out.println(tUser);

    }

}
