package pt.estgp.domem.model;

import java.io.Serializable;
import java.util.Date;

public class AreaManutAux implements Serializable{
	
	private static final long serialVersionUID = -3879030867934747043L;
	
	private Integer id;
	private String descricao;
	private Date starttime;
	private Date endtime;
	private boolean estado;	
	private boolean locked;
	
	public AreaManutAux () {};
	
	public AreaManutAux (String descricao, Date starttime, Date endtime){		
		this.descricao = descricao;
		this.starttime = starttime;
		this.endtime = endtime;				
	}

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

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
			
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "AreaManutAux [descricao=" + descricao + ", starttime=" + starttime + ", endtime="+endtime+"]";
	}
	
	public String printTrueAreaManutAux() {
		return "AreaManutAux [descricao=" + descricao + ", estado=" + estado + ", locked="+locked+"]";
	}

}
