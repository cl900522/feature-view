package acme.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated
public class Person extends BaseEntity {
    private String firstName;
    private String lastName;
    private String birthDate;
    private String gender;

    private Address address;
}
