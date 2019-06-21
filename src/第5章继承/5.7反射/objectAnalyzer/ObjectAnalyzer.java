import java.lang.reflect.*;
import java.util.ArrayList;

public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<>();
    public String toString(Object obj) {
        return toString(obj, 4);
    }
    String getIndent(int indent) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < indent; i++)
            sb.append(' ');
        return sb.toString();
    }
    public String toString(Object obj, int indent) {
        if(obj == null) return "null";
        if(visited.contains(obj)) return "...";
        visited.add(obj);
        Class cl = obj.getClass();
        if(cl == String.class) return (String) obj;
        // array
        if(cl.isArray()) {
            String r = cl.getComponentType() + "[]{";
            for(int i = 0; i < Array.getLength(obj); i++) {
                if(i > 0) r += ",";
                r += "\n" + getIndent(indent);
                Object val = Array.get(obj, i);
                if(cl.getComponentType().isPrimitive()) r += val;
                else r += toString(val, indent + 4);
            }
            return r + "\n" + getIndent(indent - 4) + "}";
        }
        // field of this and super class
        String r = cl.getName();
        do {
            r += "[";
            Field[] fields = cl.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for(Field f : fields) {
                if(!Modifier.isStatic(f.getModifiers())) {
                    if(!r.endsWith("[")) r += ",";
                    r += "\n" + getIndent(indent) + f.getName() + "=";
                    try {
                        Class t = f.getType();
                        Object val = f.get(obj);
                        if(t.isPrimitive()) r += val;
                        else r += toString(val, indent + 4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!r.endsWith("["))
                r += "\n" + getIndent(indent - 4);
            r += "]";
            cl = cl.getSuperclass();
        }while(cl != null);
        return r;
    }
}