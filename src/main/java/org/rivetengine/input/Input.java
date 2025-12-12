package org.rivetengine.input;

import java.util.ArrayList;
import java.util.List;

public class Input {
    public static List<Mouse> getConnectedMice() {
        return new ArrayList<>(List.of(new Mouse()));
    }

    public static List<Keyboard> getConnectedKeyboards() {
        return new ArrayList<>(List.of(new Keyboard()));
    }
}
