package acme.me.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbEntity extends MetaEntity {
    private Long id;
    private Integer vs;
}
