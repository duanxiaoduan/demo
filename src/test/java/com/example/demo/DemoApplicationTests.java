package com.example.demo;

import com.example.demo.entity.ShortUrl;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.ShortUrlService;
import com.example.demo.service.UserInfoService;
import com.example.demo.util.SyncTest;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() {
        //UserInfo userInfo = userInfoService.save(UserInfo.builder().userName("siri").build());
        //UserInfo userInfo = userInfoService.save(null);
        //UserInfo userInfo = (UserInfo)userInfoService.findById(17L).get();
        //Assert.assertNotNull(userInfo);
        Map<Integer, String> map = Maps.newHashMapWithExpectedSize(2);
        String[] urls = SyncTest.getUrls();
        for (int i = 0; i < urls.length; i++) {
            long hash = SyncTest.hash(urls[i]);
            if (hash < 0) {
                hash = - hash;
            }
            String encode = SyncTest.encode(hash);
            ShortUrl build = ShortUrl.builder()
                    .time(Instant.now().toEpochMilli())
                    .urlHash(hash)
                    .shortUrl(encode)
                    .url(urls[i])
                    .build();
            shortUrlService.save(build);
        }

    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testDemoController() throws Exception {

        mockMvc.perform(post("/api/person")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("")
        );
    }

}
