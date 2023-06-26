package pt.estgp.domem.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="deviceprotocol")
public class DeviceProtocol implements Serializable{

	private static final long serialVersionUID = 1281597260413063560L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;

	@NotEmpty
	@Column(name="nome", unique=true, nullable=false)
	private String nome;
	
	@NotEmpty
	@Column(name="pilightnome", nullable=false)
	private String pilightnome;	               
	
	@Column(name="versaopilight")
	private String versaopilight;
	
	
	@ManyToOne
	@JoinColumn(name = "protocoltypeid", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private ProtocolType protocoltype;
	
	
	@Column(name="created_at")
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updated_at = new Date();
		
	
	public ProtocolType getProtocoltype() {
		return protocoltype;
	}

	public void setProtocoltype(ProtocolType protocoltype) {
		this.protocoltype = protocoltype;
	}
		
	
	public String getPilightnome() {
		return pilightnome;
	}

	public void setPilightnome(String pilightnome) {
		this.pilightnome = pilightnome;
	}
		
	public String getVersaopilight() {
		return versaopilight;
	}

	public void setVersaopilight(String versaopilight) {
		this.versaopilight = versaopilight;
	}
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
		
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date data) {
		this.created_at = data;
	}
	
	
								
	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DeviceProtocol))
			return false;
		DeviceProtocol other = (DeviceProtocol) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceProtocol [id=" + id + ", nome=" + nome + "]";
	}

}
