int main ( ) {
    Connect "jdbc:oracle:thin:@rising-sun.microlab.cs.utexas.edu:1521:orcl" "cs345_18" "orcl_7857";

    tuple t1 = Select "jz101" ("hello", "at the" , ?x);
    tuple t2 = Select "jz101" (?x, "at the" , "world");

    return 0;
}
