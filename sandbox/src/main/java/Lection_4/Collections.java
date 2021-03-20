package Lection_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
    public static void main (String[] args) {
        String[] langs = {"Java", "C#", "Puthon", "PHP"};
        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("C#");
        languages.add("Python");

        List<String> languages2 = Arrays.asList("Java", "C#", "Puthon", "PHP");
        List languages3 = Arrays.asList("Java", "C#", "Puthon", "PHP");

        for (Object l : languages3) {
            System.out.println("I want to study " + l);
        }

        for (int i = 0; i < languages2.size(); i++) {
            System.out.println("I want to study " + languages2.get(i));
        }

        for (String l : languages2) {
            System.out.println("I want to study " + l);
        }

        for (int i = 0; i < langs.length; i++) {
            System.out.println("I want to study " + langs[i]);
        };

        for (String l : langs) {
            System.out.println("I want to study " + l);
        }

        for (String l : languages) {
            System.out.println("I want to study " + l);
        }
    }
}
