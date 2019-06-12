package xyz.warspear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.warspear.entity.po.Item;
import xyz.warspear.entity.po.Pic;

import java.util.List;

@Repository
public interface PicRepository extends JpaRepository<Pic,Integer>, JpaSpecificationExecutor<Pic> {
    @Query(value = "select * from pic where item_id is null",nativeQuery = true)
    public List<Pic> findBadPic ();
}
