package pt.estgp.domem.wsendpoints;

public interface WsGeneralEndpoint {
	
	void identifyAndSubscribe(String user);

	void unSubscribe(String user);

}
