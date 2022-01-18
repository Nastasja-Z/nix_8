package ua.com.alevel.hw_7_data_table_jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.hw_7_data_table_jdbc.store.ConnectionStoreFactory;

@SpringBootApplication
public class Hw7DataTableJdbcApplication {

	private final ConnectionStoreFactory connectionStoreFactory;

	public Hw7DataTableJdbcApplication(ConnectionStoreFactory connectionStoreFactory) {
		this.connectionStoreFactory = connectionStoreFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(Hw7DataTableJdbcApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initContext() {
		connectionStoreFactory.init();
	}

}
