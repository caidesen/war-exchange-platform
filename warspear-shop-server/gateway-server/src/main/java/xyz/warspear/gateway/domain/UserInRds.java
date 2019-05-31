package xyz.warspear.gateway.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.warspear.entity.po.Permission;
import xyz.warspear.entity.po.Role;
import xyz.warspear.entity.po.User;


import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserInRds implements Serializable {
    private String userName;
    private String password;
    private List<String> permissions;
public UserInRds(User user, String password) {
        this.userName = user.getUsername();
        this.password = password;
        Role role = user.getRole();
        List<Permission> ps = role.getPermissions();
       permissions= new ArrayList<>();
        for (Permission permission : ps) {
            permissions.add(permission.getUrl());
        }
    }
}
