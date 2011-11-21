int a, b; 
int factorial(int n) { 
   int x; 

   if(n < 1) { 
      showstack "hello world";
      return 1; 
   } 
   else 
   { 
      return n * factorial(n - 1); 
   } 
} 

int main() { 
   int number, answer; 
   number = 5; 
   answer = factorial(number); 
   showstack "end of recursion";
   print(answer); 
} 
