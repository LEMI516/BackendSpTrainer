package com.sptrainer.notificacion.jwt;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String pass = new BCryptPasswordEncoder().encode("pass");
        if (StringUtils.isNotEmpty(username) && username.contains("user")) {
            return new JwtUser(RandomUtil.randomString(8), username, pass, "USER", true);
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }
}
