package com.powernod.junit.Service;

import org.junit.Assert;
import org.junit.Test;

public class MothServicTest {
    @Test
    public void testMothService(){
        MothService mothService = new MothService();
        int sum = mothService.sum(1, 2);
        int sub = mothService.sub(5, 1);
//        实际值,期望值.
//        Assert.assertEquals("期望值","实际值");
        int expected = sum;
        int actual = 3;
//        添加断言机制
        Assert.assertEquals(expected,actual);
    }

}
