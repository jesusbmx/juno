package juno.content;

import java.io.IOException;

public interface LocalStorage {

    Dictionary get() throws IOException;

    void set(Dictionary dictionary) throws IOException;
}