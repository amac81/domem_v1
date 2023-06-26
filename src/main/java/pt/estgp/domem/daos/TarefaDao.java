package pt.estgp.domem.daos;

import java.util.List;

import pt.estgp.domem.model.Tarefa;
import pt.estgp.domem.model.User;

public interface TarefaDao {

	Tarefa findById(int id);
	
	Tarefa findByDescricao(String descricao);
	
	void save(Tarefa tarefa);
	
	void deleteById(int id);	
	
	List<Tarefa> getAllTarefas();

	List<Tarefa> findByUserId(User user);
	
}

