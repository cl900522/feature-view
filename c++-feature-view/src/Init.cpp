#include <stdio.h>
#include <stdlib.h>
#include <time.h>

using namespace std;

int main() {
    for(int i=0; i<100; i++){
        unsigned seed = (unsigned) time(NULL);
        printf("Seed:%d", seed);
        srand(seed);
        printf("Result:%d\n",rand());
    }
}
