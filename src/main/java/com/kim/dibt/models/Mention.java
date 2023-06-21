package com.kim.dibt.models;

import com.kim.dibt.core.models.Auditable;
import com.kim.dibt.security.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Table(name = "mentions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SQLDelete(sql = "UPDATE mentions SET  deleted=true,deleted_date=CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedMentionFilter", parameters = @ParamDef(name = "idDeleted", type = Boolean.class))
@Filter(name = "deletedMentionFilter", condition = "deleted = :idDeleted")
@Where(clause = "deleted = false")
public class Mention extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    Post post;
}
