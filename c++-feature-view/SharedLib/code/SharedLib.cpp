#include <iostream>
#include "SharedLib.h"

using std::cout;
using std::endl;

SharedLib::SharedLib() {

}

SharedLib::~SharedLib() {
}

void SharedLib::speak(char* words) {
    cout << words << endl;
}
