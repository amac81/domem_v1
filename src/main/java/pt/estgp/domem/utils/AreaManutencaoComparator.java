package pt.estgp.domem.utils;

import java.util.Comparator;

import pt.estgp.domem.model.AreaManutencao;

public class AreaManutencaoComparator implements Comparator<AreaManutencao> {

	@Override
	public int compare(AreaManutencao am1, AreaManutencao am2) {
        return am1.getDescricao().compareToIgnoreCase(am2.getDescricao());
	}
		

}