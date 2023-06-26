package pt.estgp.domem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="protocoltype")
public class ProtocolType implements Serializable{

	private static final long serialVersionUID = 5905105212041391081L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	
	@OneToMany(mappedBy = "protocoltype")
	private Set<DeviceProtocol> devicesWithProtocols;

	@NotEmpty
	@Column(name="descricao", unique=true, nullable=false)
	private String descricao;
	
	@Column(name="created_at")
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updated_at = new Date();
			
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date data) {
		this.created_at = data;
	}
	
	
								
	public Set<DeviceProtocol> getDevicesWithProtocols() {
		return devicesWithProtocols;
	}

	public void setDevicesWithProtocols(Set<DeviceProtocol> devicesWithProtocols) {
		this.devicesWithProtocols = devicesWithProtocols;
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
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProtocolType))
			return false;
		ProtocolType other = (ProtocolType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProtocolType [id=" + id + ", descricao=" + descricao + "]";
	}

}
