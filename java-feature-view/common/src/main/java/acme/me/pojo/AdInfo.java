package acme.me.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdInfo {
    private Long dbId;
    private String title;
    private String author;
    private String content;
    private Long created;
    private Long modified;
    private Integer viewed = 0;
    private Float star = 2.0f;
}
