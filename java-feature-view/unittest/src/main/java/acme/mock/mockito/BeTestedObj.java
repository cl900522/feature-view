package acme.mock.mockito;

import acme.domain.Person;

public class BeTestedObj {
    public void say(Person person) {
    }

    public void outTime(Long time) {
        try {
            Thread.sleep(time);
        }catch (Exception e) {
        }
    }
}
