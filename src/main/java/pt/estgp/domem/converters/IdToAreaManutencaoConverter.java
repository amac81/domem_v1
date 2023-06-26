package pt.estgp.domem.converters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pt.estgp.domem.model.AreaManutencao;
import pt.estgp.domem.services.AreaManutencaoService;

/**
 * A converter class used in views to map id's to actual areaManutencao objects.
 */
@Component
public class IdToAreaManutencaoConverter implements Converter<Object, AreaManutencao>{

	private static final Logger logger = Logger.getLogger("");
	
	@Autowired
	AreaManutencaoService areaManutencaoService;

	/**
	 * Gets AreaManutencao by Id
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	public AreaManutencao convert(Object element) {
		
		//if(!element.getClass().getCanonicalName().equals(AreaManutencao.class.getCanonicalName()))
	
			Integer id = Integer.parseInt((String)element);
			AreaManutencao areaManutencao = areaManutencaoService.findById(id);
	
			return areaManutencao;
		
		
	}

}