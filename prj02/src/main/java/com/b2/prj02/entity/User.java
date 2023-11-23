package com.b2.prj02.entity;

import com.b2.prj02.role.CustomGrantedAuthority;
import com.b2.prj02.role.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {
    @javax.persistence.Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long userId;

    private String email;
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    private String fileName;
    private String filePath;

//    @Column(name = "phone_number")
//    private String phoneNumber;

    private String address;
    private String gender;

    @Column(name = "pay_money")
    private Integer payMoney;

    @Enumerated(EnumType.STRING)
    private UserStatus status;



    public User updateStatus(UserStatus newStatus) {
        this.status = newStatus;
        return this;
    }

    private Integer stack;

    public void addStack() {
        this.stack++;
    }

    public void resetStack(){
        this.stack = 0;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new CustomGrantedAuthority(this.status));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
