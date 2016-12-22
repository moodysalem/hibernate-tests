package model.enumembeddablemap;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Participant {
    public Participant(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public Participant() {
    }

    @Basic
    private String name;
    @Basic
    private Integer number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(getName(), that.getName()) &&
            Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumber());
    }

    @Override
    public String toString() {
        return "Participant{" +
            "name='" + name + '\'' +
            ", number=" + number +
            '}';
    }
}
