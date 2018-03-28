package cn.xiaoyaoji.test;

import cn.xiaoyaoji.utils.PasswordUtils;
import org.junit.Test;

/**
 * @author zhoujingjie
 * @date 2016-10-10
 */
public class TestPwd {

    @Test
    public void test(){
        System.out.println(PasswordUtils.password("123456"));
        //System.out.println(StringUtils.password("123456"));
    }
}
