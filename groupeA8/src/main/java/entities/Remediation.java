package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Remediation implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private int tutoId;
    
    
    public Remediation(String date, int tuto) {
        this.date = date;
        this.tutoId = tuto;
    }
    
    public Remediation() {}

    
    
	public int getTutoId() {
		return tutoId;
	}

	public void setTutoId(int tutoId) {
		this.tutoId = tutoId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
}
