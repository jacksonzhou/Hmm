int main( ) {

    Connect "jdbc:oracle:thin:@rising-sun.microlab.cs.utexas.edu:1521:orcl" "cs345_18" "orcl_7857";

    CreateDatabase "jz1";  

    String r = "hello";
    String s = "world";
    
    Insert "jz1" (r+s, "friends", "mike");
    Insert "jz1" (r+s, "friends", "peter");
    Insert "jz1" (r+s, "enemies", "bob");

    // ("hellworld", "friends", "mike");

    Triple q = Select "jz1" (r+s, ?x , "mike");

    Insert "jz1" q;

    //[("hellworld", "friends", "mike)]

    Select "jz1" (r+s, ?x , ?y);
    Select "jz1" (r+s, "friends" , ?y);

    return 0;
}
