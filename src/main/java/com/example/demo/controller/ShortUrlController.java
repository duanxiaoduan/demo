package com.example.demo.controller;

import com.example.demo.entity.ShortUrl;
import com.example.demo.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author duanxiaoduan
 * @version 2020/1/2
 */
@RequestMapping("/url/")
@Controller
public class ShortUrlController {

    @Resource
    private ShortUrlService<ShortUrl> shortUrlService;

    @RequestMapping(value = "{url}", method = RequestMethod.GET)
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, @PathVariable String url) throws IOException {
        final String sourceUrl = getUrl(url);
        if (StringUtils.isNotBlank(sourceUrl)) {
            response.sendRedirect(sourceUrl);
        } else {
            response.setStatus(404);
        }
    }

    private String getUrl(String sUrl) {
        return shortUrlService.findByShortUrl(sUrl).orElseGet(ShortUrl::new).getUrl();
    }
}
