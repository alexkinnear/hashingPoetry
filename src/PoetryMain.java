import java.io.FileNotFoundException;

public class PoetryMain {
    public static void main(String[] args) throws FileNotFoundException {

        WritePoetry poem = new WritePoetry();
        System.out.println(poem.WritePoem("green.txt", "sam", 20, true));
        System.out.println(poem.WritePoem("test.txt", "that", 20, true));
        System.out.println(poem.WritePoem("clown.txt", "go", 20, true));
        System.out.println(poem.WritePoem("inch.txt", "computer", 50, false));
        System.out.println(poem.WritePoem("Poe.txt", "nevermore", 50, false));
        System.out.println(poem.WritePoem("Seuss.txt", "mordecai", 50, false));

    }

}
