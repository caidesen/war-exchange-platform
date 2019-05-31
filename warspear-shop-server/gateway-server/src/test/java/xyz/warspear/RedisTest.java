package xyz.warspear;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.warspear.utils.RedisUtils;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        redisUtils.set("token", strings);
    }

}
