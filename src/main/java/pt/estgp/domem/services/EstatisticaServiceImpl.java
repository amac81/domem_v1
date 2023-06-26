package pt.estgp.domem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.EstatisticaDao;
import pt.estgp.domem.model.Estatistica;


@Service("estatisticaService")
@Transactional
public class EstatisticaServiceImpl implements EstatisticaService{

	@Autowired
	private EstatisticaDao dao;
		
	public Estatistica findById(int id) {
		return dao.findById(id);
	}

	public Estatistica findByDescricao(String descricao) {
		Estatistica estatistica = dao.findByDescricao(descricao);
		return estatistica;
	}

	public void saveEstatistica(Estatistica estatistica) {
		dao.save(estatistica);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateEstatistica(Estatistica estatistica) {
		Estatistica entity = dao.findById(estatistica.getId());
		if(entity!=null){
			entity.setId(estatistica.getId());
			entity.setDescricao(estatistica.getDescricao());
			entity.setValor(estatistica.getValor());
		}
	}
	
	public void deleteEstatisticaById(int id) {
		dao.deleteById(id);
	}

	public List<Estatistica> getAll() {
		return dao.getAllEstatisticas();
	}		
	
}
