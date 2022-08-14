package net.tntninja2.mtmod.util.nbtUtil;

import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Random;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.random.RandomGenerator;

public class Util {

    public static String firstLettersToUpper(String s) {

        String capitalizedWord = "";

        Iterator<String> words = Arrays.stream(s.split("\\s")).iterator();
        while (words.hasNext()) {
            String word = words.next();
            String first = word.substring(0,1);
            String rest = word.substring(1);
            capitalizedWord += first.toUpperCase() + rest + (words.hasNext()? " ": "");
        }
        return capitalizedWord;
    }

    public static String randomStringElement(List<String> list) {
        int listLength = list.size();
        int i = (int) ( Math.random() * listLength);
        return list.get(i);
    }

    public static int randomIntInRange(int min, int max) {

        int result = min + ((int)(Math.random() * (max - min + 1)));
        return result;
    }

}
