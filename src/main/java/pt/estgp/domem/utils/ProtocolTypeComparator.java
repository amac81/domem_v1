package pt.estgp.domem.utils;

import java.util.Comparator;

import pt.estgp.domem.model.ProtocolType;

public class ProtocolTypeComparator implements Comparator<ProtocolType> {

	@Override
	public int compare(ProtocolType pt1, ProtocolType pt2) {
        return pt1.getDescricao().compareToIgnoreCase(pt2.getDescricao());
	}
		

}