int main ( ) {
    int main2 = test(3);
    showstack "break 2";
    return 0;
}

int test ( int x ) {
    showstack "hello world";
    return x;
}
