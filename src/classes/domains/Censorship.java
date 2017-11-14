package classes.domains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Censorship {
    private List<String> bannedTerms;

    public Censorship() throws IOException {
        bannedTerms = new ArrayList<String>();
        Scanner s = new Scanner(new File("src/Client.resources/swearWords.txt"));
        while (s.hasNextLine()){
            bannedTerms.add(s.nextLine());
        }
        s.close();
    }

    public List<String> getBannedTerms() {
        return bannedTerms;
    }
}
