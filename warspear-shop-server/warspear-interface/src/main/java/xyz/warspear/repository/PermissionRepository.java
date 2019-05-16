package xyz.warspear.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.warspear.entity.po.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    //Permission findByPId(Integer pId);
}
