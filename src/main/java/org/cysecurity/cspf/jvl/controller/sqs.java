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
			getId(list[0]);
		}
	}
	
	List<Message> read(){
		try{
			AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
			List<Message> messages = sqs.receiveMessage("examplequeue").getMessages();
			return messages;
		} catch (Exception ex){
			//
		}
		return null;
	}
	
	String getId(string data){
		try{
			Connection con=DriverManager.getConnection("jdbc:mysql://db.com:3306/core", USER, PASS);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT id FROM t where data = '" + data + "'");
			return rs.getString("Id");
		} catch (Exception exc){
			//
		}
		return null;
	}
}