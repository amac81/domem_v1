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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="device")
public class Device implements Serializable{

	private static final long serialVersionUID = 3656927648859809126L;

	public Device() {
		super();		
	}

	public Device(String descricao, String nome_pilight, String estado, Integer id_id, Integer id_unit,
			NodePilight nodePilight, DeviceProtocol deviceProtocol, DeviceGroup deviceGroup, boolean locked) {
		super();

		this.descricao = descricao;
		this.nome_pilight = nome_pilight;
		this.estado = estado;
		this.id_id = id_id;
		this.id_unit = id_unit;
		this.nodePilight = nodePilight;
		this.deviceProtocol = deviceProtocol;
		this.deviceGroup = deviceGroup;
		this.locked = locked;		
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;

	@NotEmpty
	@Column(name="descricao", nullable=false)
	private String descricao;
	
	@NotEmpty
	@Column(name="nome_pilight", unique=true, nullable=false)
	private String nome_pilight;	

	@Column(name="estado")
	private String estado;

	
	@Column(name="id_id")
	private Integer id_id;
	
	@Column(name="id_unit")
	private Integer id_unit;
	
	@ManyToOne
	@JoinColumn(name = "nodepilightid", referencedColumnName = "id")
	private NodePilight nodePilight;
	
	@ManyToOne
	@JoinColumn(name = "deviceprotocolid", referencedColumnName = "id")
	private DeviceProtocol deviceProtocol;
	
	@ManyToOne
	@JoinColumn(name = "devicegroupid", referencedColumnName = "id")
	private DeviceGroup deviceGroup;
	
	@Column(name="locked", nullable=false)
	private boolean locked;
			
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
	public String getDescricao() {
		return this.descricao;
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

	public String getNome_pilight() {
		return nome_pilight;
	}

	public void setNome_pilight(String nome_pilight) {
		this.nome_pilight = nome_pilight;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getId_id() {
		return id_id;
	}

	public void setId_id(Integer id_id) {
		this.id_id = id_id;
	}

	public Integer getId_unit() {
		return id_unit;
	}

	public void setId_unit(Integer id_unit) {
		this.id_unit = id_unit;
	}

	public DeviceProtocol getDeviceProtocol() {
		return deviceProtocol;
	}

	public void setDeviceProtocol(DeviceProtocol deviceProtocol) {
		this.deviceProtocol = deviceProtocol;
	}

	public DeviceGroup getDeviceGroup() {
		return deviceGroup;
	}

	public void setDeviceGroup(DeviceGroup deviceGroup) {
		this.deviceGroup = deviceGroup;
	}

	
	public NodePilight getNodePilight() {
		return nodePilight;
	}

	public void setNodePilight(NodePilight nodePilight) {
		this.nodePilight = nodePilight;
	}
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public int getLocked() {
		
		if(this.locked)
			return 1;
		
		return 0;			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome_pilight == null) ? 0 : nome_pilight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Device))
			return false;
		Device other = (Device) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome_pilight == null) {
			if (other.nome_pilight != null)
				return false;
		} else if (!nome_pilight.equals(other.nome_pilight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Device [id= " + id + ", nome_pilight= " + nome_pilight +
				" nodePilight= " + nodePilight + " deviceProtocol= " + deviceProtocol + " deviceGroup= " + deviceGroup + "]";
	}

		
}
