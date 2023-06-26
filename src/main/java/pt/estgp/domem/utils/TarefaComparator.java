package pt.estgp.domem.utils;

import java.util.Comparator;

import pt.estgp.domem.model.Tarefa;

public class TarefaComparator implements Comparator<Tarefa> {

	@Override
	public int compare(Tarefa t1, Tarefa t2) {
        return t1.getDescricao().compareToIgnoreCase(t2.getDescricao());
	}
		

}