package acme.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address extends BaseEntity {
    private String city;
    private String country;
    private String province;
    private String address;
}
