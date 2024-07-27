
package juno.io;

import java.io.File;

public final class Paths {
    
    private Paths() {}
    
    public static String join(char separator, Object[] args) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0 && sb.charAt(sb.length() - 1) != separator) {
                sb.append(separator);
            }
            sb.append(args[i]);
        }
        return sb.toString();
    }
    
    public static String join(Object... args) {
        return join(File.separatorChar, args);
    }
}
