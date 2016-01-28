import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.System.getenv;

public class AlarisConnectionFactory {
    public ConnectionFactory getFactory(){
      ConnectionFactory factory = new ConnectionFactory();
      final URI ampqUrl;
      try {
          ampqUrl = new URI(getEnvOrThrow("CLOUDAMQP_URL"));
      } catch (URISyntaxException e) {
          throw new RuntimeException(e);
      }

      factory.setUsername(ampqUrl.getUserInfo().split(":")[0]);
      factory.setPassword(ampqUrl.getUserInfo().split(":")[1]);
      factory.setHost(ampqUrl.getHost());
      factory.setPort(ampqUrl.getPort());
      factory.setVirtualHost(ampqUrl.getPath().substring(1));

      return factory;
    }

     private static String getEnvOrThrow(String name) {
      final String env = getenv(name);
      if (env == null) {
          throw new IllegalStateException("Environment variable [" + name + "] is not set.");
      }
      return env;
    }
}

