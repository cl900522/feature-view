package acme.me.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MetaEntity {
    private Date createTime;
    private Date updateTime;

}
