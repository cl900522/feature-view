#include <vector>
#include <iostream>

#include <boost/thread.hpp>

#include "data/MysqlUtil.h"

using boost::thread;
using std::cout;
using std::endl;

void hello() {
    cout << "Thread is running" << endl;
}

int main() {
    MysqlUtil util = MysqlUtil("127.0.0.1", "root", "123456", "mysql");
    util.init();
    util.query("show tables");
    util.close();

    std::vector<int> v1;
    v1.push_back(12);
    cout << "v1's first data is:" << v1[0]<<endl;

    thread thrd(&hello);
    thrd.join();
    return 0;

}

