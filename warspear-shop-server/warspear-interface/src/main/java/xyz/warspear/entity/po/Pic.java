package xyz.warspear.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Pic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer picId;
    private String picUrl;
    private Integer state;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
