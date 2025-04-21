package hr.algebra.semregprojectfrontend.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Registration {

    private Long id;
    private Student student;
    private Seminar seminar;
    private LocalDateTime registeredAt;

    public Registration() {
    }

    public Registration(Long id, Student student, Seminar seminar, LocalDateTime registeredAt) {
        this.id = id;
        this.student = student;
        this.seminar = seminar;
        this.registeredAt = registeredAt;
    }

    public Registration(Object o, String studentName, String seminarTopic) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Seminar getSeminar() {
        return seminar;
    }

    public void setSeminar(Seminar seminar) {
        this.seminar = seminar;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return Objects.equals(id, that.id) && Objects.equals(student, that.student) && Objects.equals(seminar, that.seminar) && Objects.equals(registeredAt, that.registeredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, seminar, registeredAt);
    }

    @Override
    public String toString() {
        return student.getName() + " - " + seminar.getTopic() + " - " + registeredAt;
    }

    public void setStudentName(String text) {
    }

    public void setSeminarTopic(String text) {
        
    }
}
