import com.rabbitmq.client.*;

import java.io.IOException;

public class Receiver {
	//TODO(VJ) - this should be in the configuration class
	private final static String QUEUE_NAME = "hello";
	  

	public void listen() throws Exception {
		final ConnectionFactory factory = new AlarisConnectionFactory().getFactory();
		final Connection connection = factory.newConnection();

		// // create a listener container, which is required for asynchronous
		// message consumption.
		// // AmqpTemplate cannot be used in this case
		// final SimpleMessageListenerContainer listenerContainer = new
		// SimpleMessageListenerContainer();
		// listenerContainer.setConnectionFactory(rabbitConnectionFactory);
		// listenerContainer.setQueueNames(rabbitQueue.getName());

		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {

				String message = new String(body, "UTF-8");

				System.out.println(" [x] Received '" + message + "'");
				try {
					doWork(message);
				} finally {
					System.out.println(" [x] Done");
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

	private static void doWork(String task) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException _ignored) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public static void main(String[] argv) throws Exception {
		new Receiver().listen();
	}
}
