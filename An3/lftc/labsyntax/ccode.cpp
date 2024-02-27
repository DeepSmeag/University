#include <iostream>

using namespace std;

int main()
{
    // sum of n numbers
    int mamamalalal;
    int n, s = 0;
    cin >> n;
    for (int i = 0; i < n; i = i + 1)
    {
        int x;
        cin >> x;
        // adding
        s = s + x;
    }
    cout << "Sum = " << s;
    return 0;
}