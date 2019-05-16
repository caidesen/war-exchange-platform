package xyz.warspear.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(length = 15)
    private String username;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String email;
    @Column(length = 15)
    private String qqNum;
    private String description;
    //修改时间
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
    //创建时间
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;
    //映射 user-role 多对一关系,由用户维护与角色的关系
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    //映射 role-item 一对多关系,由item维护关系
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Item> items;
    //映射 user-pic 一对多关系,由pic维护关系
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pic> pics;
}
