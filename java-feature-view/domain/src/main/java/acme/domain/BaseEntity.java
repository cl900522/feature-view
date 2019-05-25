package acme.domain;

import acme.anno.ExcelAnno;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@ExcelAnno
public class BaseEntity {
    private Long id;
    private Date created;
    private Date modified;
}
