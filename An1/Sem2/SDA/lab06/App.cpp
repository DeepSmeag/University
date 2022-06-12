#include <iostream>

#include "Teste/TestScurt.h"
#include "Teste/TestExtins.h"
#include <chrono>
#include <ctime>

int main()
{
    auto start = std::chrono::system_clock::now();
    testAll();
    testAllExtins();
    std::cout << "Finished Tests!" << std::endl;
    auto end = std::chrono::system_clock::now();
    std::chrono::duration<double> elapsed_seconds = end - start;
    std::time_t start_time = std::chrono::system_clock::to_time_t(start);
    std::time_t end_time = std::chrono::system_clock::to_time_t(end);
    std::cout << "Started computation at " << std::ctime(&start_time) << "\n";
    std::cout << "finished computation at " << std::ctime(&end_time)
              << "elapsed time: " << elapsed_seconds.count() << "s\n";
}
