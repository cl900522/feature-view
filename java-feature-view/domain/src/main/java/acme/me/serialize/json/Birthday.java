package acme.me.serialize.json;

import org.codehaus.jackson.annotate.JsonProperty;

public class Birthday {
    private String birthday;

    public Birthday(String birthday) {
        super();
        this.birthday = birthday;
    }

    /**
     * set the tag of birthday to "BIRTHDAY" instead of "birthday"
     * PS:it is also used for deserialization json
     * @return
     */
    @JsonProperty(value = "BIRTHDAY")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Birthday() {
    }

    @Override
    public String toString() {
        return this.birthday;
    }
}
