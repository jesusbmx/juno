
package juno.io;

import java.io.File;

public final class Paths {
    
    private Paths() {}
    
    public static String join(char separator, Object[] paths) {
        if (paths == null || paths.length == 0) {
            return "";
        }

        StringBuilder fullPath = new StringBuilder();

        for (Object part : paths) {
            if (part == null) {
                continue;
            }

            String path = part.toString();
            if (fullPath.length() > 0) {
                if (fullPath.charAt(fullPath.length() - 1) != separator &&
                    path.charAt(0) != separator) {
                    fullPath.append(separator);
                } else if (fullPath.charAt(fullPath.length() - 1) == separator &&
                           path.charAt(0) == separator) {
                    path = path.substring(1);  // Remove leading separator
                }
            }
            fullPath.append(path);
        }

        return fullPath.toString();
    }
    
    public static String join(Object... args) {
        return join(File.separatorChar, args);
    }
}
