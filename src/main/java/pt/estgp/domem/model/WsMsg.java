package pt.estgp.domem.model;

import java.io.Serializable;


public class WsMsg implements Serializable{

	private static final long serialVersionUID = -6151068059016016579L;

	private WsMsgType msgType;
	
	private Object msgContent;
	
	public WsMsg() {
	    
	}
			
	public WsMsgType getMsgType() {
		return msgType;
	}


	public void setMsgType(WsMsgType usersinfo) {
		this.msgType = usersinfo;
	}


	public Object getMsgContent() {
		return msgContent;
	}


	public void setMsgContent(Object msgContent) {
		this.msgContent = msgContent;
	}


	@Override
	public String toString() {
		return "WsMsg [msgType=" + msgType + ", msgContent=" + msgContent + "]";
	}
	
}
