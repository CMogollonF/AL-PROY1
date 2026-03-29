

public class Relations {
    public static int FetchCode(char letter){
        if(letter > 'a' && letter < 'z') return letter - 'a';
        else if (letter > 'A' && letter < 'Z') return letter - 'A';
        else if (letter == '.') return 26;
        else if (letter == ',') return 27;
        else if (letter == ' ') return 28;
        else return -1;
    }   

}
