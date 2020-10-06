package messageQ;

import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

class DoLogic{
	
	void execute() {
		List<Message> list = read();
		if (list != null && list.count() > 0) {
			String CreditCard = getId(list[0]);
			DoTask(CreditCard);
		}
	}
	
	List<Message> read(){
		try{
			AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
			List<Message> messages = sqs.receiveMessage("queue145").getMessages();
			return messages;
		} catch (Exception ex){
			//
		}
		return null;
	}
}