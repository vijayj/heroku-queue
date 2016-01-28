import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Send {

  private final static String QUEUE_NAME = "hello";

  public void send(String message) throws Exception{

    ConnectionFactory factory = new AlarisConnectionFactory().getFactory();

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//    String message = "Hello World!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }

//
//  public static void main(String[] argv) throws Exception {
//    new Send().send("hi there");
//  }


}




