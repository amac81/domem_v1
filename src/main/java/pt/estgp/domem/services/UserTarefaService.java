package pt.estgp.domem.services;

import java.util.List;

import pt.estgp.domem.model.Tarefa;


public interface UserTarefaService {

	Tarefa findById(int id);

	Tarefa findByType(String type);
	
	List<Tarefa> getAll();
	
}
