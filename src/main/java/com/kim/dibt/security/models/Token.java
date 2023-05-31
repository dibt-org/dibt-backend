package com.kim.dibt.security.models;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import com.kim.dibt.core.models.Auditable;
import org.hibernate.annotations.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "tokens")
@SQLDelete(sql = "UPDATE tokens SET deleted=true,deleted_date=CURRENT_TIMESTAMP WHERE id = ?")
@FilterDef(name = "deletedTokenFilter", parameters = @ParamDef(name = "idDeleted", type = Boolean.class))
@Filter(name = "deletedTokenFilter", condition = "deleted = :idDeleted")
@Where(clause = "deleted = false")
public class Token extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}
