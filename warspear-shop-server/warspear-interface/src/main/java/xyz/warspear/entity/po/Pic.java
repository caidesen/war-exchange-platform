package xyz.warspear.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Pic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer picId;
    private String picUri;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
