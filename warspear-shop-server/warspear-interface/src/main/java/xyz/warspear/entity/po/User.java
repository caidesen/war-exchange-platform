package xyz.warspear.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import xyz.warspear.Annotation.IsQQ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 15, message = "用户名长度必须大于2，最大15")
    @Column(length = 15, unique = true)
    private String username;

    @NotBlank(message = "用户密码不能为空")
    @Column(length = 32)
    private String password;

    @NotBlank(message = "邮箱不为空")
    @Email(message = "邮件格式不对")
    @Column(length = 100, unique = true)
    private String email;

    @NotBlank(message = "qq号不能为空")
    @Column(length = 15)
    @IsQQ
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    //映射 role-item 一对多关系,由item维护关系
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Item> items;
    //映射 user-pic 一对多关系,由pic维护关系
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pic> pics;
}
