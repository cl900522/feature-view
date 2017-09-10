/*
 * TestDynamicLib.cpp
 *
 *  Created on: 2017年2月11日
 *      Author: chenmx
 */

#include <iostream>
#include <dlfcn.h>
using namespace std;

typedef int (*CAC_FUNC)(int, int);

void runDynamicLoad() {
    void *handle;
    char *error;
    char *path = "../../lib/libDynamicLib.so";
    /* 开启之前所撰写的 libmylib.so 链接库 */
    handle = dlopen(path,
    RTLD_NOW);
    if (!handle) {
        cout << "Error loading dyamic lib" << endl;
        return;
    }

    CAC_FUNC cac_func = NULL;
    *(void **) (&cac_func) = dlsym(handle, "plus");
    if ((error = dlerror()) != NULL) {
        cout << "Error get plus function" << error << endl;
    }

    int plusR = cac_func(2, 7);
    cout << "plus 2 and 7 is:" << plusR << endl;

    *(void **) (&cac_func) = dlsym(handle, "minus");
    if ((error = dlerror()) != NULL) {
        cout << "Error get minus function" << error << endl;
    }

    int minusR = cac_func(10, 7);
    cout << "minus 10 and 7 is:" << minusR << endl;
    dlclose(handle);
}

int main() {
    runDynamicLoad();
}

