package pt.estgp.domem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="manutencaoschedule")
public class ManutencaoSchedule implements Serializable{

	private static final long serialVersionUID = 4545786967680774008L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Integer id;

	@NotEmpty
	@Column(name="descricao", unique=true, nullable=false)
	private String descricao;
	
	@Column(name="created_at")
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updated_at = new Date();

	@Column(name="starttime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date starttime;
	
	@Column(name="endtime")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date endtime;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "manutencaoschedule__areamanutencao", 
             joinColumns = { @JoinColumn(name = "manutencaoschedule_id") }, 
             inverseJoinColumns = { @JoinColumn(name = "areamanutencao_id") })
	private Set<AreaManutencao> areasManutencao = new HashSet<AreaManutencao>();
		
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
						
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date startime) {
		this.starttime = startime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Set<AreaManutencao> getAreasManutencao() {
		return areasManutencao;
	}

	public void setAreasManutencao(Set<AreaManutencao> scheduleEntryAreasManutencao) {
		this.areasManutencao = scheduleEntryAreasManutencao;
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
		if (!(obj instanceof ManutencaoSchedule))
			return false;
		ManutencaoSchedule other = (ManutencaoSchedule) obj;
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
		return "ManutencaoSchedule [id=" + id + ", descricao=" + descricao + ", starttime=" + starttime + ", endtime="+endtime+"]:"
				+ " AREAS=["+areasManutencao+"]";
	}
	
	
}
