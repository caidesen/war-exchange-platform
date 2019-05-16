package xyz.warspear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.warspear.entity.po.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    //Role findByRoleId(Integer roleId);
}
