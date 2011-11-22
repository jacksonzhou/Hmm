string hello () {
    return "hello";
}

int main ( ) {
    Connect "jdbc:oracle:thin:@rising-sun.microlab.cs.utexas.edu:1521:orcl" "cs345_18" "orcl_7857";

    string a = hello();
    string b = "good";
    string c = "bye";

    Insert "jz101" (a, "at the", "sun");
    Insert "jz101" (a, "at the", "moon");
    Insert "jz101" (a, "at the", "earth");
    Insert "jz101" (a, "at the", "world");

    Insert "jz101" (b ^ c, "at the", "sun");
    Insert "jz101" (b ^ c, "at the", "moon");
    Insert "jz101" (b ^ c, "at the", "earth");
    Insert "jz101" (b ^ c, "at the", "world");
}
