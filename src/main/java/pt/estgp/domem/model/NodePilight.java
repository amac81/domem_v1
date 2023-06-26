package pt.estgp.domem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="nodepilight")
public class NodePilight implements Serializable{

	private static final long serialVersionUID = 950990453967125758L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="descricao", nullable=true)
	private String descricao;

	@Column(name="uuid", nullable=false)
	private String uuid;
	
	@Column(name="localizacao", nullable=false)
	private String localizacao;
	
	@Column(name="ipaddress", nullable=false)
	private String ipaddress;
	
	/*activo/desactivo*/
	@Column(name="estado", nullable=false)
	private boolean estado;
		
	@Column(name="created_at")
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updated_at = new Date();
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="nodePilight")	
	private List<Device> nodePilightDevices = new ArrayList<Device>();

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public boolean isEstado() {
		return estado;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
	public String getIpAddress() {
		return ipaddress;
	}

	public void setIpAddress(String ipaddress) {
		this.ipaddress = ipaddress;
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

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public List<Device> getNodePilightDevices() {
		return nodePilightDevices;
	}

	public void setNodePilightDevices(List<Device> nodePilightDevices) {
		this.nodePilightDevices = nodePilightDevices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserProfile))
			return false;
		NodePilight other = (NodePilight) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodePilight [id=" + id + ", uuid=" + uuid + ", estado= " + estado + " ,localizacacao=" + localizacao + ", ipaddress=" + ipaddress+"]";
	}



}
