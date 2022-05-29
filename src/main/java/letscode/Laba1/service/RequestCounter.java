package letscode.Laba1.service;

import org.springframework.stereotype.Service;

@Service
public class RequestCounter {
    private static int counter;

    public RequestCounter() {
        counter = 0;
    }

    public static void increase() {
        ++counter;
    }

    public static Integer getCounter() {
        return counter;
    }
}
