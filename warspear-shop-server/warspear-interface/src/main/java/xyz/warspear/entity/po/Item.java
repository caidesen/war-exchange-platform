package xyz.warspear.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import xyz.warspear.repository.ItemRepository;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer itemId;
    @Column(length = 15)
    private String title;
    @Column(nullable = false)
    private String server;
    @Column(nullable = false)
    private boolean isSell;
    private String exchangeType;
    private String equipmentType;
    private String weaponType;
    private String className;
    private String tag;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer priceGold;
    private Integer priceRMB;
    //修改时间
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
    //创建时间
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;
    //映射 item-pic 一对多关系,由pic维护关系，具备级联操作
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private List<Pic> pics;
    //映射 item-user 多对一关系，由item维护关系
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
