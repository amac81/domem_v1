package pt.estgp.domem.wsendpoints;

public interface WsDomoticEndpoint {
	
	boolean sendToPilight (String newMessage, String userName);

}
