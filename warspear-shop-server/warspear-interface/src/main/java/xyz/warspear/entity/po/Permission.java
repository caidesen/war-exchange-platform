package xyz.warspear.entity.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Permission {
    @Id
    @Column(unique = true, nullable = false)
    private Integer pId;
    private String permission;
    private String url;
    private String description;
}
