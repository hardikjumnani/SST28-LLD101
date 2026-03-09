import java.util.*;

public class DeviceRegistry {
    private final java.util.List<Object> devices = new ArrayList<>();

    public void add(Object d) { devices.add(d); }

    public <T> T getFirstOfType(Class<T> type) {
        for (Object d : devices) {
            if (type.isInstance(d)) return type.cast(d);
        }
        throw new IllegalStateException("Missing: " + type.getSimpleName());
    }

    public Object getFirstOfType(String simpleName) {
        for (Object d : devices) {
            if (d.getClass().getSimpleName().equals(simpleName)) return d;
        }
        throw new IllegalStateException("Missing: " + simpleName);
    }
}
