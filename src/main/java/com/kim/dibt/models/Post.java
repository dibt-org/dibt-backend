package com.kim.dibt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kim.dibt.core.models.Auditable;
import com.kim.dibt.security.models.User;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Table(name = "posts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE posts SET  deleted=true,deleted_date=CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedPostFilter", parameters = @ParamDef(name = "idDeleted", type = Boolean.class))
@Filter(name = "deletedPostFilter", condition = "deleted = :idDeleted")
@Where(clause = "deleted = false")
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String title;
    @Column(length = 1000)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Mention> mentions;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Media> medias;
}
