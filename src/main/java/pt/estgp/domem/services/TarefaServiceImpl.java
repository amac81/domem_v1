package pt.estgp.domem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.estgp.domem.daos.TarefaDao;
import pt.estgp.domem.model.Tarefa;
import pt.estgp.domem.model.User;


@Service("tarefaService")
@Transactional
public class TarefaServiceImpl implements TarefaService{	
	

	@Autowired
	private TarefaDao dao;

		
	public Tarefa findById(int id) {
		return dao.findById(id);
	}

	public Tarefa findByDescricao(String descricao) {
		Tarefa tarefa = dao.findByDescricao(descricao);
		return tarefa;
	}
	
	public boolean alreadyExists(String descricao, User user) {
		
		for(Tarefa t: user.getUserTarefas())
			if(t.getDescricao().equals(descricao))
				return true;
		
		return false;
	}

	public void saveTarefa(Tarefa tarefa) {
		dao.save(tarefa);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateTarefa(Tarefa tarefa) {
		Tarefa entity = dao.findById(tarefa.getId());
		if(entity!=null){
			entity.setId(tarefa.getId());
			entity.setEstado(tarefa.isEstado());			
			entity.setDescricao(tarefa.getDescricao());
		}
	}
	
	public void deleteTarefaById(int id) {
		dao.deleteById(id);
	}

	public List<Tarefa> getAll() {
		return dao.getAllTarefas();
	}

	
}
