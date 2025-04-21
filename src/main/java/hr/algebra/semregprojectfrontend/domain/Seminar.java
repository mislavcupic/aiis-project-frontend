package hr.algebra.semregprojectfrontend.domain;

import java.util.Objects;

public class Seminar {

    private Long id;
    private String topic;
    private String lecturer;

    public Seminar() {
    }

    public Seminar(Long id, String topic, String lecturer) {
        this.id = id;
        this.topic = topic;
        this.lecturer = lecturer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seminar seminar = (Seminar) o;
        return Objects.equals(id, seminar.id) && Objects.equals(topic, seminar.topic) && Objects.equals(lecturer, seminar.lecturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, lecturer);
    }

    @Override
    public String toString() {
        return topic + " - " + lecturer;
    }
}
