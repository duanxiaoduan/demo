package com.example.demo.entity;


import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author duanxiaoduan
 * @version 2018/3/29
 */
@Entity
@Table(name = "tb_user_info")
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class UserInfo extends AbstractEntity implements Serializable {

    /**
     * 昵称
     */
    @Column(name = "user_name")
    private String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return super.getId().equals(userInfo.getId()) &&
                userName.equals(userInfo.userName);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), userName);
    }
}
