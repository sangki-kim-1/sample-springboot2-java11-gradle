package com.example.samplespringboot2javagradle

import com.example.samplespringboot2javagradle.config.security.UserDetailsImpl
import com.example.samplespringboot2javagradle.fixture.MemberFixture
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class TestUserDetailsServiceImpl implements UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def roleList = []
        if (usename == 'user@email.com') {
            roleList = ["ROLE_USER"]
        } else if (username == 'admin@email.com') {
            roleList = ["ROLE_ADMIN"]
        }
        def member = MemberFixture.userDetails(username, roleList as String)
        return new UserDetailsImpl(member)
    }
}
