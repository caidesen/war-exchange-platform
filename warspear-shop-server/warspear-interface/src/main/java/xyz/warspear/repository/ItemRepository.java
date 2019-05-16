package xyz.warspear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.warspear.entity.po.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
}
