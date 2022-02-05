package ua.com.alevel.hw_7_data_table_jdbc.exception;

public class EntityExistException extends RuntimeException {

    public EntityExistException(String msg) {
        super(msg);
    }
}
