package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SingletonInizializer {
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public void init(String path, HashMap map) throws IOException {
        fileReader = new FileReader(path);
        bufferedReader = new BufferedReader(fileReader);
        String line;
        StringTokenizer st;
        while((line = bufferedReader.readLine()) != null) {
            st = new StringTokenizer(line,",");
            map.put(ResourcePacks.valueOf(st.nextToken()),st.nextToken());

        }
    }
}
