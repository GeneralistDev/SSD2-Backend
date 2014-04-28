package models;

/**
 * Created by daniel on 28/04/14.
 */
public class Relationship {
    public long id;
    public Person one;
    public Person two;
    public String relationship;

    public Relationship(long id, Person one, Person two, String relationship) {
        this.id = id;
        this.one = one;
        this.two = two;
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        String relationString = one.name + " and " + two.name + " are " + relationship;
        return relationString;
    }
}
