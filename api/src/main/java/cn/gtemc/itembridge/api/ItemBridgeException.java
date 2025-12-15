package cn.gtemc.itembridge.api;

public class ItemBridgeException extends RuntimeException {

    public ItemBridgeException(String message) {
        super(message);
    }

    public ItemBridgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
