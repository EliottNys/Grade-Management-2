package GradeManagement.GradeManagement.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Honorpow ")
public class Honor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "honor_generator")
    private Long id;

    @Column(name = "honor")
    private String honor;

    @Column(name = "grade")
    private Integer grade;



    @Column(name = "degree")
    private String degree;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    public Long getId() {
        return id;
    }

    public String getHonor() {
        return honor;
    }

    public Integer getGrade() {
        return grade;
    }

    public Student getStudent() {
        return student;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Honor(){
    }

    public Honor(Student student, String honor, Integer grade,String degree ) {
        this.student = student;
        this.honor = honor;
        this.grade = grade;
        this.degree = degree;
    }
}
