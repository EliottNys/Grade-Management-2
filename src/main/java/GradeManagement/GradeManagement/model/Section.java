package GradeManagement.GradeManagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "section_generator")
    private long id;

    @Column(name = "name")
    private String name;


    @Column(name = "credits")
    private Integer credits;

    @Column(name = "level")
    private Integer level;

    public Section(){
    }

    public Section(String name, Integer credits,Integer level) {
        this.name = name;
        this.credits = credits;
        this.level = level;
      }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public Set<Course> getCourses() {
    //     return courses;
    // }

    // public void setCourses(Set<Course> courses) {
    //     this.courses = courses;
    // }
    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Section [id=" + id + ", name=" + name + "]";
    }
}
