package pt.estgp.domem.services;

import java.util.List;

import pt.estgp.domem.model.Tarefa;
import pt.estgp.domem.model.User;

public interface TarefaService {
	
	Tarefa findById(int id);
	
	Tarefa findByDescricao(String descricao);
	
    boolean alreadyExists(String descricao, User user);
	
	void saveTarefa(Tarefa tarefa);
	
	void updateTarefa(Tarefa tarefa);
	
	void deleteTarefaById(int id);
	
	List<Tarefa> getAll();		

}