/*
 * ShareLibTest.cpp
 *
 *  Created on: 2017年2月11日
 *      Author: chenmx
 */

#include <iostream>
#include <dlfcn.h>
#include "SharedLib.h"
using namespace std;

typedef int (*CAC_FUNC)(char*);

void runSharedLib() {
    SharedLib lib;
    lib.speak("Hello Sharead fun");
}

void runDynamicLoad() {
    void *handle;
    char *error;
    /* 开启之前所撰写的 libmylib.so 链接库 */
    handle = dlopen("../../../lib/libSharedLib.so", RTLD_LAZY);
    if (!handle) {
        cout << "Error loading dyamic lib" << endl;
        return;
    }

    CAC_FUNC cac_func = NULL;
    *(void **) (&cac_func) = dlsym(handle, "speak");
    if ((error = dlerror()) != NULL) {
        cout << "Error get speak function" << endl;
        cout << error;
    }

    cac_func("Hello Dynamic Fun");
    dlclose(handle);
}

int main() {
    runSharedLib();
}

