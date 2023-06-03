package com.kim.dibt.models;

import com.kim.dibt.core.models.Auditable;
import com.kim.dibt.security.models.User;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE comments SET  deleted=true,deleted_date=CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedCommentFilter", parameters = @ParamDef(name = "idDeleted", type = Boolean.class))
@Filter(name = "deletedCommentFilter", condition = "deleted = :idDeleted")
@Where(clause = "deleted = false")
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, updatable = false)
    Post post;


}
