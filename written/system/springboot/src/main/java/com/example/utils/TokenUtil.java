package com.example.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.Constant;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
@Component
public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    @Resource
    private AdminService adminService;

    private static AdminService staticAdminService;

    @PostConstruct
    public void init() {
        staticAdminService = adminService;
    }
    
    public static String createToken(String data, String sign) {
        return JWT.create()
            .withAudience(data)
            .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
            .sign(Algorithm.HMAC256(sign));
    }
    
    public static Account getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constant.TOKEN);
            String audience = JWT.decode(token).getAudience().get(0);
            Integer id = Integer.valueOf(audience.split("-")[0]);
            String role = audience.split("-")[1];
            if (RoleEnum.ADMIN.name().equals(role)) {
                return staticAdminService.selectById(id);
            }
        } catch (Exception e) {
            log.error("获取当前用户失败", e);
        }
        return null;
    }

}