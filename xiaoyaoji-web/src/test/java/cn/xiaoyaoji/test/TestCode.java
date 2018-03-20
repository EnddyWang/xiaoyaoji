package cn.xiaoyaoji.test;

import cn.xiaoyaoji.core.util.StringUtils;
import org.junit.Test;

/**
 * @author: zhoujingjie
 * @Date: 16/8/21
 */
public class TestCode {

    @Test
    public void test(){
        for(int i=0;i<1000;i++) {
            System.out.println(StringUtils.code(10));
        }
    }

    @Test
    public void testChp(){
        System.out.println("2_0".compareTo("2_0_1"));
        System.out.println("2_1".compareTo("2_0_1"));
    }

    @Test
    public void test3(){
        String str="<img class=\"imgToggle\" data-status=\"1\" src=\"http://f1.xiaoyaoji.cn/assets/jsonformat/images/Expanded.gif\"><img class=\"imgToggle\" data-status=\"1\" src=\"http://f1.xiaoyaoji.cn/assets/jsonformat/images/Expanded.gif\"/>";
        str=str.replaceAll("<img(.*?)>","<img$1/>").replaceAll("<img(.*?)//>","<img$1/>");
        System.out.println(str);
    }
}
