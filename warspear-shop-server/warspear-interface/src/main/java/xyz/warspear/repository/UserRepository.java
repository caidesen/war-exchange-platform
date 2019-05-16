package xyz.warspear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.warspear.entity.po.Item;
import xyz.warspear.entity.po.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
