package acme.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BaseEntity {
    private Long id;
    private Date created;
    private Date modified;
}
