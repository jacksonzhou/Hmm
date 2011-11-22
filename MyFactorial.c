int a, b; 
int factorial(int n) { 
   int x; 

   if(n < 1) { 
       showstack "break1";
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
   showstack "break 2";
   print(answer); 
} 
