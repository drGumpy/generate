package certyficate.generate;

import certyficate.entitys.Order;

public class Special {
	private static final String SPECIAL_CLIENT_1 = "";
	private static final String SPECIAL_CLIENT_2 = "";
	
	private static final String SPECIAL_DEVICE_1 = "";
	
	private static final String CHANNEL_1_TEMPLATE = "sw_T.ods"; 
	private static final String CHANNEL_2_TEMPLATE = "sw_Tx2.ods"; 
	
	private static final String SPECIAL_COMMENT = "";
	
	private Order order;
	
	private String[] comment;
	
	private String template;
	
	private int channelNumber;
	
	public Special(Order order) {
		this.order = order;
		setComment();
		setChannelNumber();
		setTemplate();
	}

	public String[] getComments() {
		return comment;
	}
	
	public int getChannelNumber() {
		return channelNumber;
	}
	
	public String getTemplate() {
		return template;
	}
	
	private void setComment() {
		if(SPECIAL_DEVICE_1.equals(order.getDevice().getModel())) {
			setCommentsText();
		}
	}
	
	private void setCommentsText() {
		comment = new String[2];
		comment[0] = checkCient();
		comment[1] = SPECIAL_COMMENT;
	}

	private String checkCient() {
		String comment = "";
		if(SPECIAL_CLIENT_2.equals(order.getDeclarant().getName())) {
			comment = SPECIAL_COMMENT;
		}
		return comment;
	}

	private void setChannelNumber() {
		channelNumber = order.getChannelNumber();
		if(SPECIAL_CLIENT_1.equals(order.getDeclarant().getName())) {
			channelNumber = 2;
		}
	}

	private void setTemplate() {
		if(channelNumber > 1) {
			template = CHANNEL_2_TEMPLATE;
		} else {
			template = CHANNEL_1_TEMPLATE;
		}		
	}
}
