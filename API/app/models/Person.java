package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 28/04/14.
 */
public class Person {
    public String name;
    public long id;
    public enum nodeTypes {
        person
    }
    public nodeTypes type;
    public List<Relationship> nodeRelationships;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
        this.type = nodeTypes.person;
        nodeRelationships = new ArrayList<Relationship>();
    }

    public void addRelationship(Relationship r) {
        nodeRelationships.add(r);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Relationship r: nodeRelationships) {
            stringBuilder.append(r.toString());
        }
        return stringBuilder.toString();
    }
}
